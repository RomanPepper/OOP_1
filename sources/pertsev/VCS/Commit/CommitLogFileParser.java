package pertsev.VCS.Commit;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommitLogFileParser {
    private Path commitLogFile;

    public CommitLogFileParser(Path commitLofFile) {
        this.commitLogFile = commitLofFile;
    }

    public Queue<Commit> readCommitQueue() throws IOException {
        String fileText = Files.readAllLines(commitLogFile).toString();
        Queue<Commit> commits = new LinkedList<>();

        for (String commitText : fileText.split(CommitLogConstants.COMMIT_SEPARATOR)) {
            Commit commit;
            String commitName = Arrays.copyOfRange(commitText.split("\n"), 0, 1)[0].trim();
            Map<Path, List<Change>> fileChanges = new HashMap<>();

            //Массив изменений для каждого файла
            String[] changeSetsText = commitText.split(CommitLogConstants.CHANGE_SET_SEPARATOR);
            for (String changeSetText : changeSetsText) {
                String[] changeSetLines = changeSetText.split("\n");

                Path changeSetPath = Paths.get(changeSetLines[0].trim());
                List<Change> changes = new ArrayList<>();

                for (int i = 1; i < changeSetLines.length; i++) {
                    Change change;
                    Change.LineShiftPointer lineShiftPointer;
                    String portableString;

                    String[] coordinatesAndString = changeSetLines[i].split("\\|");

                    int oldCoordinate = Integer.parseInt(coordinatesAndString[0].split(",")[0]);
                    int newCoordinate = Integer.parseInt(coordinatesAndString[0].split(",")[1]);

                    lineShiftPointer = new Change.LineShiftPointer(oldCoordinate, newCoordinate);
                    portableString = coordinatesAndString[1];

                    change = new Change(lineShiftPointer, portableString);

                    changes.add(change);
                }

                fileChanges.put(changeSetPath, changes);
            }

            commit = new Commit(commitName, fileChanges);
            commits.add(commit);
        }

        return commits;
    }
}
