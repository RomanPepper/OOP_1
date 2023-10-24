package pertsev.VCS.Commit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Commit {
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");

    //ВОПРОC: оставлять константами + нужно ли переходить на UPPER_CASE или final тут не нужен?
    private final String name;
    private final Map<Path, List<Change>> fileChanges;


    public Commit(String name, Map<Path, List<Change>> fileChanges) {
        this.name = name;
        this.fileChanges = fileChanges;
    }


    public String getName() {
        return name;
    }

    public Map<Path, List<Change>> getFileChanges() {
        return new HashMap<>(fileChanges);
    }
}
