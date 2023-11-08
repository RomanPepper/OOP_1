package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

public class ChangeDelete implements Change {
    @Override
    public void apply(FileState file) {
        file.setExist(false);
        file.setValue(null);
    }

    @Override
    public String toStringValue() {
        return CommitLogConstants.DELETED_FILE_MARKER;
    }
}
