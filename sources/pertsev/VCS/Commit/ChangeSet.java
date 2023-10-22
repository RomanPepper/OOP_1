package pertsev.VCS.Commit;

public class ChangeSet {
    enum Type {
        LINE_CHANGE,
        LINE_MOVE
    }

    private final Type type;
    private final String portableString;
    private final int oldLineIndex;
    private final int newLineIndex;

    private ChangeSet(Type type, int oldLineIndex, int newLineIndex, String portableString) {
        this.type = type;
        this.oldLineIndex = oldLineIndex;
        this.newLineIndex = newLineIndex;
        this.portableString = portableString;
    }

    public Type getType() {
        return type;
    }

    public int getOldLineIndex() {
        return oldLineIndex;
    }

    public int getNewLineIndex() {
        return newLineIndex;
    }

    public String getPortableString() {
        return portableString;
    }
}
