package pertsev.VCS.Commit;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CommitLogConstants {
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    public static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    public static final String COMMIT_SEPARATOR = "------COMMIT_END------";
    public static final String FILE_STRUCTURE_SEPARATOR = "---FILE_STRUCTURE_END---";
    public static final String CHANGE_SET_SEPARATOR = "---CHANGESET_END---";
    public static final String DELETED_FILE_MARKER = "-/FILE_HAS_BEEN_DELETED/-";
    public static final String CREATED_FILE_MARKER = "-/FILE_HAS_BEEN_CREATED/-";
    public static final String LINE_INDEX_AND_STRING_SEPARATOR = "-|/|-";
}
