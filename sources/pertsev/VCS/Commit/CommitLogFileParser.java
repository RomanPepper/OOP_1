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
        String fileText = String.join("\n", Files.readAllLines(commitLogFile));
        Queue<Commit> commits = new LinkedList<>();

        if (fileText.isEmpty()) return commits;

        for (String commitText : fileText.split(CommitLogConstants.COMMIT_SEPARATOR)) {
            Commit commit;
            String commitName = commitText.trim().split("\n")[0].trim();
            Path[] fileStructureArray;
            Map<Path, List<Change>> fileChanges = new HashMap<>();

            //Уберем из текста имя коммита
            String[] textRowsWithoutCommitName = commitText.trim().split("\n");
            commitText = String.join("\n", Arrays.copyOfRange(textRowsWithoutCommitName,
                    1, textRowsWithoutCommitName.length));

            //Разделим текст на две части: файловую структуру и список ченджсетов
            String fileStructurePiece = commitText.split(CommitLogConstants.FILE_STRUCTURE_SEPARATOR)[0].trim();
            String changeSetsPiece = commitText.split(CommitLogConstants.FILE_STRUCTURE_SEPARATOR)[1].trim();

            //Запишем файловую структуру
            fileStructureArray = parseFileStructure(fileStructurePiece.split("\n"));

            //Массив изменений для каждого файла
            String[] changeSetsText = changeSetsPiece.split(CommitLogConstants.CHANGE_SET_SEPARATOR);
            for (String changeSetText : changeSetsText) {
                String[] changeSetLines = parseChangeSetRecords(changeSetText.trim());

                Path changeSetPath = Paths.get(changeSetLines[0].trim());
                List<Change> changes = new ArrayList<>();

                //Начинаем идти со второй строки, т.к. первая - путь к файлу
                for (int i = 1; i < changeSetLines.length; i++) {
                    Change change;
                    int lineIndex;
                    String portableString;

                    if (isEditFileChange(changeSetLines[i])) {
                        String[] lineIndexAndString = changeSetLines[i]
                                .split(CommitLogConstants.INDEX_AND_STRING_LINE_SEPARATOR_REGEX);

                        if (lineIndexAndString.length == 1)
                            lineIndexAndString = new String[]{lineIndexAndString[0], ""};

                        lineIndex = Integer.parseInt(lineIndexAndString[0]);
                        portableString = lineIndexAndString[1];

                        change = new ChangeEdit(lineIndex, portableString);
                    } else if (isDeleteFileChange(changeSetLines[i])) { //Если удаление, то...
                        change = new ChangeDelete();
                    } else { //Если создание, то...
                        change = new ChangeCreate();
                    }

                    changes.add(change);
                }

                fileChanges.put(changeSetPath, changes);
            }

            commit = new Commit(commitName, fileStructureArray, fileChanges);
            commits.add(commit);
        }

        return commits;
    }

    private boolean isEditFileChange(String changeString) {
        return changeString.contains(CommitLogConstants.INDEX_AND_STRING_LINE_SEPARATOR);
    }

    private boolean isDeleteFileChange(String changeString) {
        return changeString.trim().equals(CommitLogConstants.DELETED_FILE_MARKER);
    }

    private String[] parseChangeSetRecords(String changeSetText) {
        List<String> list = new ArrayList<>();
        int indent = CommitLogConstants.CHANGE_SET_LINE_END_SEPARATOR.length();

        int index = changeSetText.indexOf("\n");
        list.add(changeSetText.substring(0, index));

        for (int i = index + 1; i < changeSetText.length() - indent; i++) {
            int shift = 0;
            boolean addIndent = false;
            //Ищем начало такой строки
            if (changeSetText.substring(i, i + indent)
                    .equals(CommitLogConstants.CHANGE_SET_LINE_START_SEPARATOR)) {
                //Ищем конец
                for (int j = i + 1; j < changeSetText.length() - indent + 1; j++) {
                    if (changeSetText.substring(j, j + indent)
                            .equals(CommitLogConstants.CHANGE_SET_LINE_END_SEPARATOR)) {
                        //По нахождении, добавляем то, что между
                        list.add(changeSetText.substring(i + indent, j));
                        addIndent = true;
                        shift = j + indent;
                        break;
                    }
                }
            }
            if (addIndent) {
                i = shift - 1;
            }
        }

        return list.toArray(new String[0]);
    }

    private Path[] parseFileStructure(String[] lines) {
        List<Path> list = new ArrayList<>();
        for (String line : lines) {
            list.add(Paths.get(line));
        }
        return list.toArray(new Path[0]);
    }
}
