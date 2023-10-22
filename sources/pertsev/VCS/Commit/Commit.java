package pertsev.VCS.Commit;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Commit {
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    private String name;
    private Map<Path, ChangeSet> fileChanges;


    public Commit(String name, Map<Path, ChangeSet> fileChanges) {
        this.name = name;
        this.fileChanges = fileChanges;
    }


    public String getName() {
        return name;
    }

    public Map<Path, ChangeSet> getFileChanges() {
        return new HashMap<>(fileChanges);
    }
}
