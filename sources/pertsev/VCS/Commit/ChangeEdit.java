package pertsev.VCS.Commit;

import java.nio.file.Path;

public class ChangeEdit implements Change {
    private final int lineIndex;
    private final String replacementString;

    public ChangeEdit(int lineIndex, String replacementString) {
        this.lineIndex = lineIndex;
        this.replacementString = replacementString;
    }

    @Override
    public FileValueWrapper apply(String[] linedText, Path path) {
        linedText[lineIndex] = replacementString;
        return new EditedFileValueWrapper(String.join("\n", linedText));
    }

    @Override
    public String toStringValue() {
        return lineIndex + CommitLogConstants.LINE_INDEX_AND_STRING_SEPARATOR + replacementString;
    }
}
