package pertsev.VCS.FileUtils;

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
        private List<String> diffList = new ArrayList<>();

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


            return FileVisitResult.CONTINUE;
        }

        public List<String> getDiffList() {
            return new ArrayList<>(diffList);
        }
    }

    private Path directory;

    public FileTracker(Path directory) {
        this.directory = directory;
    }

    public String status() throws IOException {
        FileTrackerVisitor fileVisitor = new FileTrackerVisitor();
        Files.walkFileTree(this.directory, fileVisitor);
        return fileVisitor.getDiffList().toString();
    }
}
