package pertsev.VCS.VCSUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository {
    private final Path repoPath;

    public Repository(Path repoPath) {
        this.repoPath = repoPath;
    }

    public Path getRepoPath() {
        return repoPath;
    }
}