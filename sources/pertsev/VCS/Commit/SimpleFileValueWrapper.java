package pertsev.VCS.Commit;

public class SimpleFileValueWrapper extends FileValueWrapper{
    public SimpleFileValueWrapper(String value) {
        super(value);
    }

    @Override
    public Type getType() {
        return Type.SIMPLE;
    }
}
