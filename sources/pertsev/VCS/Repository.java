package pertsev.VCS;

import pertsev.VCS.FileHandlers.FilePatcher;
import pertsev.VCS.FileHandlers.FileTracker;

import java.nio.file.Path;

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