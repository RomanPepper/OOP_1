package pertsev.VCS.Commit;

import java.io.IOException;

public class CommitQueueController {
    private final CommitQueue commitQueue;
    private final CommitLogFileParser commitLogFileParser;

    public CommitQueueController(CommitQueue commitQueue, CommitLogFileParser commitLogFileParser) {
        this.commitQueue = commitQueue;
        this.commitLogFileParser = commitLogFileParser;
    }

    public void reread() throws IOException {
        commitQueue.load(commitLogFileParser.readCommitQueue());
    }
}
