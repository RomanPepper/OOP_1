package pertsev.VCS.VCSUtils;

import pertsev.VCS.FileUtils.FilePatcher;
import pertsev.VCS.FileUtils.FileTracker;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository {
    private final Path repoPath;
    private FileTracker fileTracker;
    private FilePatcher filePatcher;

    public Repository(Path repoPath) {
        this.repoPath = repoPath;
        fileTracker = new FileTracker(repoPath);
        filePatcher = new FilePatcher(repoPath);
    }

    public Path getRepoPath() {
        return repoPath;
    }
}