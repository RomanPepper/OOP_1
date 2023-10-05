package pertsev.VCS.VCSUtils;

import java.nio.file.Path;

public class Commit {
    private final Path commitFile;
    private String name;
    private int hashCode;

    public Commit(Path commitFile, String name) {
        this.commitFile = commitFile;

        //делаем hashCode
        hashCode = 0;
    }

    public Path getCommitFile() {
        return commitFile;
    }

    public int getHashCode() {
        return hashCode;
    }
}
