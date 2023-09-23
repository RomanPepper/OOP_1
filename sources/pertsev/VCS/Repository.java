package pertsev.VCS;

import java.nio.file.Path;
import java.util.Stack;

public class Repository {
    private static class CommitStack extends Stack<pertsev.VCS.Commit> {
        @Override
        public synchronized Commit pop() {
            //Логика отката коммита

            return super.pop();
        }
    }
    private final Path repoPath;

    private CommitStack stack = new CommitStack();

    public Repository(Path repoPath) {
        this.repoPath = repoPath;
    }

    public Path getRepoPath() {
        return repoPath;
    }

    public void commit() {
        stack.add(new Commit(

        ));
    }

    public void reset(String commitHashCode) {
        while (!stack.peek().getHashCode().equals(commitHashCode)) {
            stack.pop();
        }
    }
}