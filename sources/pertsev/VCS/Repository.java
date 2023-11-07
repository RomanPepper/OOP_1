package pertsev.VCS;

import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Commit.CommitLogFileParser;
import pertsev.VCS.FileHandlers.CommitManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

public class Repository {
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    public static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    private CommitLogFileParser commitLogFileParser = new CommitLogFileParser(COMMITS_FILE);
    private Queue<Commit> commitQueue = commitLogFileParser.readCommitQueue();

    private CommitManager commitManager;

    public Repository(Path repoPath) throws IOException {
        commitManager = new CommitManager(repoPath, COMMITS_FILE, commitQueue);
    }

    public Queue<Commit> getCommitQueue() {
        return commitQueue;
    }

    public void commit(String commitName) throws IOException {
        commitManager.commit(commitName);

        //После осуществления коммита перезапишем значение очереди на актуальное
        commitQueue = commitLogFileParser.readCommitQueue();
    }
}