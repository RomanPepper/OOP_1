package pertsev.VCS.Commit;

import java.nio.file.Path;

public class ChangeDelete implements Change {
    @Override
    public FileValueWrapper apply(String[] linedText, Path path) {
        return new DeletedFileValueWrapper();
    }

    @Override
    public String toStringValue() {
        return CommitLogConstants.DELETED_FILE_MARKER + "\n";
    }
}
