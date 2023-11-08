package pertsev.VCS.FileHandlers;

import pertsev.VCS.Commit.*;
import pertsev.VCS.File.FileState;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class CommitManager {
    private FileTextComparator comparator = new FileTextComparator();
    private CommitQueue commitQueue;
    private CommitQueueController commitQueueController;
    private CommitCollector collector;
    private CommitLogFileWriter commitLogFileWriter;
    private FilePatcher filePatcher;
    private Path resourcesDirectory;

    public CommitManager(Path directory, Path commitLogFile,
                         CommitQueue commitQueue, CommitQueueController commitQueueController) {
        this.resourcesDirectory = directory;
        this.commitQueue = commitQueue;
        this.commitQueueController = commitQueueController;
        this.commitLogFileWriter = new CommitLogFileWriter(commitLogFile);
        this.filePatcher = new FilePatcher(directory);
        this.collector = new CommitCollector(commitQueue);
    }

    private static class FileTrackerVisitor extends SimpleFileVisitor<Path> {
        private List<Path> files = new ArrayList<>();

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            files.add(file);

            return FileVisitResult.CONTINUE;
        }

        public List<Path> getFiles() {
            return files;
        }
    }

    public void rollbackTo(String commitName) throws IOException {
        if (commitQueue.isEmpty()) return;

        while (!commitQueue.getLastCommit().name().equals(commitName)) {
            rollbackLastCommit();
        }
    }

    private void rollbackLastCommit() throws IOException {
        //Крайний случай
        if (commitQueue.isEmpty()) return;

        //Чтобы откатить последний коммит, нужно привести репозиторий к виду коммита, предшествующего последнему
        Commit penultCommit = commitQueue.getPenultCommit();

        //Все существующие на момент предпоследнего коммита файлы
        Path[] oldFiles = penultCommit.files();
        if (oldFiles == null) return;

        List<FileState> newFiles = new ArrayList<>();
        for (Path file : oldFiles) {
            newFiles.add(collector.collect(penultCommit.name(), file));
        }

        // ----Приведем репозиторий к состоянию предпоследнего коммита
        // --Отредактируем и создадим новые файлы
        for (FileState fileState : newFiles) {
            Path filePath = fileState.getPath();
            //Если такой файл уже есть
            if (Files.exists(filePath)) {
                //Перезаписать значение на актуальное
                filePatcher.patch(filePath, fileState.getValue());
            } else {//Если такого файла нет, то
                //Создаем файл с таким значением
                filePatcher.create(filePath, fileState.getValue());
            }
        }

        // --Удалим ненужные файлы
        List<Path> currFiles;
        FileTrackerVisitor fileTrackerVisitor = new FileTrackerVisitor();
        Files.walkFileTree(resourcesDirectory, fileTrackerVisitor);
        currFiles = fileTrackerVisitor.getFiles();
        List<Path> oldFilesList = List.of(oldFiles);

        for (Path currFile : currFiles) {
            // Если файл не предусмотрен в репозитории, но существует
            if (!oldFilesList.contains(currFile)) {
                Files.deleteIfExists(currFile);
            }
        }

        // Репозиторий обновлен, теперь обновим лог-файл и commitQueue
        commitLogFileWriter.pullCommit();
        commitQueueController.reread();
    }

    // Фиксируем изменения в новый коммит
    public void commit(String commitName) throws IOException {
        Commit lastCommit = commitQueue.getLastCommit();
        List<Path> currFiles;

        FileTrackerVisitor fileTrackerVisitor = new FileTrackerVisitor();
        Files.walkFileTree(resourcesDirectory, fileTrackerVisitor);
        currFiles = fileTrackerVisitor.getFiles();

        Map<Path, List<Change>> changes = new HashMap<>();

        //Если коммитов ещё не было
        if (lastCommit == null) {
            for (Path file : currFiles) {
                FileState oldFile = new FileState(file, false, null);
                FileState newFile = new FileState(file, true, readFileValue(file));
                changes.put(file, comparator.getDiffs(oldFile, newFile));
            }
            commitLogFileWriter.putCommit(new Commit(commitName, currFiles.toArray(new Path[0]), changes));
            return;
        }
        List<Path> oldFiles = new ArrayList<>(Arrays.asList(lastCommit.files()));

        //Рассмотрим случаи изменения и удаления
        for (Path oldFilePath : oldFiles) {
            FileState oldFile = collector.collect(lastCommit.name(), oldFilePath);
            if (currFiles.contains(oldFilePath)) {
                FileState newFile = new FileState(oldFilePath, true,
                        readFileValue(oldFilePath));
                List<Change> changeList = comparator.getDiffs(oldFile, newFile);
                if (!changeList.isEmpty()) {
                    changes.put(oldFilePath, changeList);
                }
            } else {
                FileState newFile = new FileState(oldFilePath, false, null);
                changes.put(oldFilePath, comparator.getDiffs(oldFile, newFile));
            }
        }

        //Рассмотрим случай добавления
        for (Path currFile : currFiles) {
            if (!changes.containsKey(currFile)) {
                FileState newFile = new FileState(currFile, true, readFileValue(currFile));
                if (oldFiles.contains(currFile)) {
                    FileState oldFile = new FileState(currFile, false, null);
                    changes.put(currFile, comparator.getDiffs(oldFile, newFile));
                }
            }
        }

        Commit newCommit = new Commit(commitName, currFiles.toArray(new Path[0]), changes);
        commitLogFileWriter.putCommit(newCommit);
        commitQueueController.reread();
    }

    private String readFileValue(Path path) throws IOException {
        return String.join("\n", Files.readAllLines(path));
    }
}
