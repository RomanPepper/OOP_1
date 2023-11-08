package pertsev.VCS.File;

import java.nio.file.Path;

//Класс, отражающий состояние и значение файла по пути Path в момент времени
public class FileState {
    private final Path path;
    private boolean exist;
    private String value;

    public FileState(Path path, boolean exist, String value) {
        this.path = path;
        this.exist = exist;
        this.value = value;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Path getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FileState{" +
                "path=" + path +
                ", exist=" + exist +
                ", value='" + value + '\'' +
                '}';
    }
}
