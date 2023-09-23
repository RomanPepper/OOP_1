package pertsev.VCS;

import java.nio.file.Path;

public class Commit {
    private Path commitsDirectoryLink;
    private String hashCode;

    public Commit() {
        //делаем hashCode
        hashCode = ...;
    }

    public String getHashCode() {
        return hashCode;
    }
}
