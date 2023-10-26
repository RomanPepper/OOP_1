package pertsev.VCS.Commit;


// Абстрактный класс переносчик-значений файла только между объектами Comparator и Collector
public abstract class FileValueWrapper {
    public enum Type {
        CREATE, DELETE, EDIT, SIMPLE
    }

    private String value;

    public FileValueWrapper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public abstract Type getType();
}
