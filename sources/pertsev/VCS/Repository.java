package pertsev.VCS;

import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Commit.CommitLogConstants;
import pertsev.VCS.Commit.CommitLogFileParser;
import pertsev.VCS.FileHandlers.FilePatcher;
import pertsev.VCS.FileHandlers.FileTracker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;
import java.util.Stack;

public class Repository {
    private CommitLogFileParser commitLogFileParser = new CommitLogFileParser(CommitLogConstants.COMMITS_FILE);
    private Queue<Commit> commitQueue = commitLogFileParser.readCommitQueue();
    private FileTracker fileTracker;
    private FilePatcher filePatcher;

    public Repository(Path repoPath) throws IOException {
        fileTracker = new FileTracker(getLastCommit(), repoPath);
        filePatcher = new FilePatcher(repoPath);
    }

    private Commit getLastCommit() {
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
}