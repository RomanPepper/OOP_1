package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commit {

    //ВОПРОC: (оставлять константами + нужно ли переходить на UPPER_CASE?) ИЛИ (final тут не нужен?)
    private final String name;

    //Список всех файлов и директорий в репозитории на момент утверждения коммита
    private final Path[] files;

    private final Map<Path, List<Change>> fileChanges;

    public Commit(String name, Path[] files, Map<Path, List<Change>> fileChanges) {
        this.name = name;
        this.files = files;
        this.fileChanges = fileChanges;
    }

    public Path[] getFiles() {
        return files;
    }

    public String getName() {
        return name;
    }

    public Map<Path, List<Change>> getFileChanges() {
        return new HashMap<>(fileChanges);
    }
}
