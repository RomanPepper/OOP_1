package pertsev.VCS.Commit;

public class ChangeEdit implements Change {
    private final int lineIndex;
    private final String replacementString;

    public ChangeEdit(int lineIndex, String replacementString) {
        this.lineIndex = lineIndex;
        this.replacementString = replacementString;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public String getReplacementString() {
        return replacementString;
    }

    @Override
    public String toStringValue() {
        return lineIndex + CommitLogConstants.LINE_INDEX_AND_STRING_SEPARATOR + replacementString;
    }
}
