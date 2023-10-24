package pertsev.VCS.Commit;

public class Change {
    private final int lineIndex;
    private final String replacementString;

    public Change(int lineIndex, String replacementString) {
        this.lineIndex = lineIndex;
        this.replacementString = replacementString;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public String getReplacementString() {
        return replacementString;
    }
}

