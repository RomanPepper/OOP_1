package pertsev.VCS.Commit;

import java.nio.file.Path;
import java.nio.file.Paths;

class CommitLogConstants {
    protected static final String PROJECT_DIR = System.getProperty("user.dir");
    protected static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    protected static final String COMMIT_SEPARATOR = "------COMMIT_END------";
    protected static final String FILE_STRUCTURE_SEPARATOR = "---FILE_STRUCTURE_END---";
    protected static final String CHANGE_SET_SEPARATOR = "---CHANGESET_END---";
    protected static final String DELETED_FILE_MARKER = "-/FILE_HAS_BEEN_DELETED/-";
    protected static final String CREATED_FILE_MARKER = "-/FILE_HAS_BEEN_CREATED/-";
    protected static final String LINE_INDEX_AND_STRING_SEPARATOR = "-|/|-";
}
