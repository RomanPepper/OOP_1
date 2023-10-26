package pertsev.VCS.FileHandlers;

import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Commit.CommitCollector;
import pertsev.VCS.Commit.CommitLogConstants;
import pertsev.VCS.Commit.SimpleFileValueWrapper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileTracker {
    private static class FileTrackerVisitor extends SimpleFileVisitor<Path> {
        private CommitCollector collector = new CommitCollector(CommitLogConstants.COMMITS_FILE);
        private FileTextComparator comparator = new FileTextComparator();
        private List<Path> changedFiles = new ArrayList<>();
        private Commit lastCommit;

        public FileTrackerVisitor(Commit lastCommit) {
            this.lastCommit = lastCommit;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            // Сравним просматриваемый файл с его последней версией
            int cmp = comparator.compare(collector.collect(lastCommit.getName(), file),
                    new SimpleFileValueWrapper(String.join("\n", Files.readAllLines(file))));



            return FileVisitResult.CONTINUE;
        }

        //Опять же ВОПРОС: что по поводу необходимости return new ArrayList<>(changedFiles)?
        public List<Path> getChangedFiles() {
            return changedFiles;
        }
    }

    private static final String NO_CHANGES_TEXT = "Nothing has changed";
    private static final String CONSOLE_INDENT = "        ";

    private Commit lastCommit;
    private Path directory;

    public FileTracker(Commit lastCommit, Path directory) {
        this.lastCommit = lastCommit;
        this.directory = directory;
    }

    public String status() throws IOException {
        FileTrackerVisitor fileVisitor = new FileTrackerVisitor(lastCommit);
        Files.walkFileTree(this.directory, fileVisitor);
        return beautifiedChangedFilesToString(fileVisitor.getChangedFiles());
    }


    private String beautifiedChangedFilesToString(List<Path> files) {
        if (files.isEmpty()) return NO_CHANGES_TEXT;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("modified: ").append(files.get(0));

        for (int i = 1; i < files.size(); i++) {
            stringBuilder.append(CONSOLE_INDENT).append(files.get(i).toUri()).append("\n");
        }
        return stringBuilder.toString();
    }
}
