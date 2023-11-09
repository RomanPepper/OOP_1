package pertsev.VCS;

import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Commit.CommitLogFileParser;
import pertsev.VCS.Commit.CommitQueue;
import pertsev.VCS.Commit.CommitQueueController;
import pertsev.VCS.FileHandlers.CommitManager;
import pertsev.VCS.PathHandlers.PathHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

public class Repository {
    private static final Path COMMITS_FILE;

    static {
        Path path = Paths.get(
                VersionControlSystem.PROJECT_DIR + "/sources/commit_log.txt"
        );
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        COMMITS_FILE = path;
    }

    private PathHandler pathHandler = new PathHandler(
            Paths.get(VersionControlSystem.PROJECT_DIR)
    );
    private CommitLogFileParser commitLogFileParser = new CommitLogFileParser(COMMITS_FILE, pathHandler);
    private CommitQueue commitQueue = new CommitQueue();
    private CommitQueueController commitQueueController = new CommitQueueController(commitQueue, commitLogFileParser);

    {
        commitQueueController.reread();
    }

    private CommitManager commitManager;

    public Repository(Path repoPath) throws IOException {
        commitManager = new CommitManager(repoPath, COMMITS_FILE,
                commitQueue, commitQueueController, pathHandler);
    }

    public Queue<Commit> getCommitQueue() {
        return commitQueue;
    }

    public void commit(String commitName) throws IOException {
        commitManager.commit(commitName);
    }

    public void rollbackTo(String commitName) throws IOException {
        commitManager.rollbackTo(commitName);
    }
}