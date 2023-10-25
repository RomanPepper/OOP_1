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

    //ДОДЕЛАТЬ ПОД УДАЛЕНИЕ ФАЙЛА
    public Queue<Commit> readCommitQueue() throws IOException {
        String fileText = Files.readAllLines(commitLogFile).toString();
        Queue<Commit> commits = new LinkedList<>();

        for (String commitText : fileText.split(CommitLogConstants.COMMIT_SEPARATOR)) {
            Commit commit;
            String commitName = commitText.split("\n")[0].trim();
            String[] fileStructureList;
            Map<Path, List<Change>> fileChanges = new HashMap<>();

            //Уберем из текста имя коммита
            String[] textRowsWithoutCommitName = commitText.split("\n");
            commitText = String.join("\n", Arrays.copyOfRange(textRowsWithoutCommitName,
                    1, textRowsWithoutCommitName.length));

            //Разделим текст на две части: файловую структуру и список ченджсетов
            String fileStructurePiece = commitText.split(CommitLogConstants.FILE_STRUCTURE_SEPARATOR)[0];
            String changeSetsPiece = commitText.split(CommitLogConstants.FILE_STRUCTURE_SEPARATOR)[1];

            //Запишем файловую структуру
            fileStructureList = fileStructurePiece.split("\n");

            //Массив изменений для каждого файла
            String[] changeSetsText = changeSetsPiece.split(CommitLogConstants.CHANGE_SET_SEPARATOR);
            for (String changeSetText : changeSetsText) {
                String[] changeSetLines = changeSetText.split("\n");

                Path changeSetPath = Paths.get(changeSetLines[0].trim());
                List<Change> changes = new ArrayList<>();

                for (int i = 1; i < changeSetLines.length; i++) {
                    Change change;
                    int lineIndex;
                    String portableString;

                    //Если текущая операция - изменение текста файла, а не удаление, то...
                    if (isEditFileChange(changeSetLines[i])) {
                        String[] lineIndexAndString = changeSetLines[i]
                                .split(CommitLogConstants.LINE_INDEX_AND_STRING_SEPARATOR);

                        lineIndex = Integer.parseInt(lineIndexAndString[0]);
                        portableString = lineIndexAndString[1];

                        change = new Change(Change.Type.TEXT_EDIT, lineIndex, portableString);
                    } else { //Если удаление, то...
                        change = new Change(Change.Type.DELETE, 0, null);
                    }

                    changes.add(change);
                }

                fileChanges.put(changeSetPath, changes);
            }

            commit = new Commit(commitName, fileStructureList, fileChanges);
            commits.add(commit);
        }

        return commits;
    }

    private boolean isEditFileChange(String changeString) {
        return changeString.split(CommitLogConstants.LINE_INDEX_AND_STRING_SEPARATOR).length == 2;
    }
}
