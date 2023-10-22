package pertsev.VCS.Commit;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CommitLogFileParser {
    private final String COMMIT_SEPARATOR = "------COMMIT_END------";
    private final String FILE_CHANGES_SEPARATOR = "---CHANGESET_END---";
    private Path commitLogFile;

    public CommitLogFileParser(Path commitLofFile) {
        this.commitLogFile = commitLofFile;
    }

    public Queue<Commit> readCommitQueue() throws IOException {
        String fileText = Files.readAllLines(commitLogFile).toString();
        Queue<Commit> commits = new LinkedList<>();

        for (String commitText : fileText.split(COMMIT_SEPARATOR)) {
            Commit commit;
            String commitName = Arrays.copyOfRange(commitText.split("\n"), 0, 1)[0].strip();

            //Массив изменений для каждого файла
            String[] fileChangesText = commitText.split(FILE_CHANGES_SEPARATOR);

            String  = Arrays.copyOfRange(fileChangesText, 0, 1)[0].strip();

            boolean isFirstLine = true;
            //Разбираем каждую строку из описания
            for (String fileChangeText: fileChangesText) {
                ChangeSet fileChange;
                String[] parse
            }

            Map<Integer, String> editedLines = new HashMap<>();
            for (int indexLine = 2; i < lines.length; i++) {
                editedLines.put(i - 2, )
            }
        }
    }
}
