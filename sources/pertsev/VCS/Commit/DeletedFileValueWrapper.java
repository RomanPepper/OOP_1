package pertsev.VCS.Commit;

public class DeletedFileValueWrapper extends FileValueWrapper {
    public DeletedFileValueWrapper() {
        super(null);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public Type getType() {
        return Type.DELETE;
    }


}
