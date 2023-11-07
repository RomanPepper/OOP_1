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
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            files.add(file);

            return FileVisitResult.CONTINUE;
        }

        public List<Path> getFiles() {
            return files;
        }
    }

    // Фиксируем изменения в новый коммит
    public void commit(String commitName) throws IOException {
        Commit lastCommit = getLastCommit();
        FileTrackerVisitor fileTrackerVisitor = new FileTrackerVisitor();
        Files.walkFileTree(resourcesDirectory, fileTrackerVisitor);
        Path[] currFiles = fileTrackerVisitor.getFiles().toArray(new Path[0]);
        Map<Path, List<Change>> map = new HashMap<>();

        //Если коммитов ещё не было
        if (lastCommit == null) {
            for (Path file : currFiles) {
                FileState oldFile = new FileState(file, false, null);
                FileState newFile = new FileState(file, true, readFileValue(file));
                map.put(file, comparator.getDiffs(oldFile, newFile));
            }
            commitLogFileWriter.putCommit(new Commit(commitName, currFiles, map));
            return;
        }
        Path[] oldFiles = lastCommit.getFiles();

        //Рассмотрим случаи изменения и удаления
        for (Path file : oldFiles) {
            FileState oldFile = collector.collect(lastCommit.getName(), file);
            boolean isFounded = false;
            for (Path currFile : currFiles) {
                if (file.equals(currFile)) {
                    isFounded = true;
                    FileState newFile = new FileState(currFile, true,
                            readFileValue(currFile));
                    List<Change> changes = comparator.getDiffs(oldFile, newFile);
                    if (!changes.isEmpty()) {
                        map.put(currFile, changes);
                    }
                    break;
                }
            }
            if (!isFounded) {
                FileState newFile = new FileState(file, false, null);
                map.put(file, comparator.getDiffs(oldFile, newFile));
            }
        }

        //Рассмотрим случай добавления
        for (Path currFile : currFiles) {
            if (!map.containsKey(currFile)) {
                FileState newFile = new FileState(currFile, true, readFileValue(currFile));
                if (!Arrays.asList(oldFiles).contains(currFile)) {
                    FileState oldFile = new FileState(currFile, false, null);
                    map.put(currFile, comparator.getDiffs(oldFile, newFile));
                }
            }
        }

        Commit newCommit = new Commit(commitName, currFiles, map);
        commitLogFileWriter.putCommit(newCommit);
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
