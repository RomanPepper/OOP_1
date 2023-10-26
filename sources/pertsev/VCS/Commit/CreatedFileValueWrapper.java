package pertsev.VCS.Commit;

public class CreatedFileValueWrapper extends FileValueWrapper {
    public CreatedFileValueWrapper() {
        super("");
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public Type getType() {
        return Type.CREATE;
    }
}