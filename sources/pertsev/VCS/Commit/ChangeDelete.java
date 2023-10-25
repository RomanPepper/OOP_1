package pertsev.VCS.Commit;

public class ChangeDelete implements Change{
    @Override
    public String toStringValue() {
        return CommitLogConstants.DELETED_FILE_MARKER + "\n";
    }
}
