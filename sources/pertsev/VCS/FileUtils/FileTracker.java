package pertsev.VCS.FileUtils;


import pertsev.VCS.VCSUtils.Repository;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileTracker {
    private static class MyFileVisitor extends SimpleFileVisitor<Path> {
        private List<List<String>> diffList = new ArrayList<>();
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {



            return FileVisitResult.CONTINUE;
        }

        public List<List<String>> getDiffList() {
            return new ArrayList<>(diffList.stream().map(ArrayList::new).toList());
        }

        public void newDiff(List<String> diff) {
            this.diffList.add(diff);
        }
    }
    private Repository repository;
    public FileTracker(Repository repository) {
        this.repository = repository;
    }

    public String status() throws IOException {
        MyFileVisitor fileVisitor = new MyFileVisitor();
        Files.walkFileTree(repository.getRepoPath(), fileVisitor);
        return fileVisitor.getDiffList().toString();
    }
}
