package pertsev.VCS.VCSUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Commit {
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    private String name;
    private Path editedFile;
    private Map<Integer, String> editedLines;

    public Commit(String name, Path editedFile,
                  Map<Integer, String> editedLines) {
        this.name = name;
        this.editedFile = editedFile;
        this.editedLines = editedLines;
    }

    public String getName() {
        return name;
    }
}
