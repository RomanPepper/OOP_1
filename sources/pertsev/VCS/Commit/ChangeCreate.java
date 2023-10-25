package pertsev.VCS.Commit;

//Изменение, включающее в себя функционал создания нового файла и его изменение
public class ChangeCreate implements Change {
    private final int lineIndex;
    private final String replacementString;

    public ChangeCreate(int lineIndex, String replacementString) {
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CommitLogConstants.CREATED_FILE_MARKER).append("\n");
        if (replacementString != null) {
            stringBuilder.append(lineIndex)
                    .append(CommitLogConstants.LINE_INDEX_AND_STRING_SEPARATOR)
                    .append(replacementString);
        }
        return stringBuilder.toString();
    }
}
