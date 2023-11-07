package pertsev.VCS.Commit;


public class CommitLogConstants {
    public static final String COMMIT_SEPARATOR = "------COMMIT_END------";
    public static final String FILE_STRUCTURE_SEPARATOR = "---FILE_STRUCTURE_END---";
    public static final String CHANGE_SET_SEPARATOR = "---CHANGESET_END---";
    public static final String DELETED_FILE_MARKER = "-/FILE_HAS_BEEN_DELETED/-";
    public static final String CREATED_FILE_MARKER = "-/FILE_HAS_BEEN_CREATED/-";
    public static final String INDEX_AND_STRING_LINE_SEPARATOR_REGEX = "-\\\\\\\\\\\\\\|\\\\\\/\\\\\\\\\\\\\\|-";
    public static final String INDEX_AND_STRING_LINE_SEPARATOR = "-\\\\\\|\\/\\\\\\|-";
    public static final String CHANGE_SET_LINE_START_SEPARATOR = "<</-\\|/\\|-/-";
    public static final String CHANGE_SET_LINE_END_SEPARATOR = "-/-\\|/\\|-/>>";
}
