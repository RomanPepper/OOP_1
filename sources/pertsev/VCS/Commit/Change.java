package pertsev.VCS.Commit;

public class Change {
    protected enum Type {
        TEXT_EDIT, ADD, DELETE
    }

    private final Type type;

    //Только для Type == TEXT_EDIT
    private final int lineIndex;
    private final String replacementString;

    //Не очень нравится реализация конструктора, в частности, нужно подавать лишние элементы для DELETE
    public Change(Type type, int lineIndex, String replacementString) {
        this.type = type;
        this.lineIndex = lineIndex;
        this.replacementString = replacementString;
    }

    public Type getType() {
        return type;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public String getReplacementString() {
        return replacementString;
    }
}

