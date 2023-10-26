package pertsev.VCS.Commit;

public class EditedFileValueWrapper extends FileValueWrapper {
    public EditedFileValueWrapper(String value) {
        super(value);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public Type getType() {
        return Type.EDIT;
    }
}
