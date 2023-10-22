package pertsev.VCS.FileHandlers;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FilePatcher {
    private static class FilePatcherVisitor extends SimpleFileVisitor<Path> {
        List<Path> patchList;

        public FilePatcherVisitor(List<Path> patchList) {
            this.patchList = patchList;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {


            return FileVisitResult.CONTINUE;
        }
    }

    private Path directory;

    public FilePatcher(Path directory) {
        this.directory = directory;
    }

    public void patch(List<Path> fileList) throws IOException {
        FilePatcherVisitor filePatcherVisitor = new FilePatcherVisitor(fileList);
        Files.walkFileTree(directory, filePatcherVisitor);
    }
}
