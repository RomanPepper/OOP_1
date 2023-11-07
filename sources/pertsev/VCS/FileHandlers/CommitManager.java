package pertsev.VCS.FileHandlers;

import pertsev.VCS.Commit.Change;
import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Commit.CommitCollector;
import pertsev.VCS.Commit.CommitLogFileWriter;
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
    private Queue<Commit> commitQueue;
    private CommitCollector collector;
    private CommitLogFileWriter commitLogFileWriter;
    private FilePatcher filePatcher;
    private Path resourcesDirectory;

    public CommitManager(Path directory, Path commitLogFile, Queue<Commit> commitQueue) {
        this.resourcesDirectory = directory;
        this.commitQueue = commitQueue;
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

        while (!getLastCommit().name().equals(commitName)) {
            rollbackLastCommit();
        }
    }

    private void rollbackLastCommit() throws IOException {
        //Крайний случай
        if (commitQueue.isEmpty()) return;

        //Чтобы откатить последний коммит, нужно привести репозиторий к виду коммита, предшествующего последнему
        Commit penultCommit = getPenultCommit();

        //Все существующие на момент предпоследнего коммита файлы
        Path[] files = penultCommit.files();
        if (files == null) return;

        List<FileState> newFiles = new ArrayList<>();
        for (Path file : files) {
            newFiles.add(collector.collect(penultCommit.name(), file));
        }

        //Приведем репозиторий к состоянию предпоследнего коммита
        for (FileState fileState : newFiles) {
            Path filePath = fileState.getPath();
            //Если такой файл уже есть
            if (filePath.toFile().exists()) {
                //Перезаписать значение на актуальное
                filePatcher.patch(filePath, fileState.getValue());
            } else {//Если такого файла нет, то
                //Создаем файл с таким значением
                filePatcher.create(filePath, fileState.getValue());
            }
        }

        //Репозиторий обновлен, теперь обновим лог-файл
        commitLogFileWriter.pullCommit();
    }

    // Фиксируем изменения в новый коммит
    public void commit(String commitName) throws IOException {
        Commit lastCommit = getLastCommit();
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

//        //Рассмотрим случай добавления
        for (Path currFile : currFiles) {
            if (!changes.containsKey(currFile)) {
                FileState newFile = new FileState(currFile, true, readFileValue(currFile));
                if (!Arrays.asList(oldFiles).contains(currFile)) {
                    FileState oldFile = new FileState(currFile, false, null);
                    changes.put(currFile, comparator.getDiffs(oldFile, newFile));
                }
            }
        }

        Commit newCommit = new Commit(commitName, currFiles.toArray(new Path[0]), changes);
        commitLogFileWriter.putCommit(newCommit);
    }

    private Commit getPenultCommit() {
        if (commitQueue.size() <= 1) return null;
        Stack<Commit> stack = new Stack<>();
        Commit penultCommit;

        //Доходим до предпоследнего коммита
        while (commitQueue.size() > 2) {
            stack.add(commitQueue.poll());
        }
        //Запоминаем его
        penultCommit = commitQueue.peek();

        //Добавляем в стек оставшиеся коммиты
        stack.add(commitQueue.poll());
        stack.add(commitQueue.poll());

        //Переливаем всё из стека обратно в очередь
        while (!stack.isEmpty()) {
            commitQueue.add(stack.pop());
        }

        return penultCommit;
    }

    private Commit getLastCommit() {
        if (commitQueue.isEmpty()) return null;
        Stack<Commit> stack = new Stack<>();
        Commit lastCommit;

        while (!commitQueue.isEmpty()) {
            stack.add(commitQueue.poll());
        }
        lastCommit = stack.peek();

        while (!stack.isEmpty()) {
            commitQueue.add(stack.pop());
        }

        return lastCommit;
    }

    private String readFileValue(Path path) throws IOException {
        return String.join("\n", Files.readAllLines(path));
    }
}
