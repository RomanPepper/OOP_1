package pertsev.VCS.Commit;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CommitLogFileWriter {
    private Path commitLogFile;

    public CommitLogFileWriter(Path commitLogFile) {
        this.commitLogFile = commitLogFile;
    }

    public void putCommit(Commit commit) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        //Добавим имя коммита
        stringBuilder.append(commit.name().trim()).append("\n");

        //Запишем файловую структуру на момент коммита
        for (Path file : commit.files()) {
            stringBuilder.append(file).append("\n");
        }

        //Разделитель
        stringBuilder.append(CommitLogConstants.FILE_STRUCTURE_SEPARATOR).append("\n");

        //Запишем изменения для всех файлов коммита:
        for (Path filePath : commit.fileChanges().keySet()) {
            //Пропишем путь к файлу
            stringBuilder.append(filePath).append("\n");

            //Запишем каждое изменение этого файла
            List<Change> changes = commit.fileChanges().get(filePath);
            for (Change change : changes) {
                stringBuilder.append(CommitLogConstants.CHANGE_SET_LINE_START_SEPARATOR)
                        .append(change.toStringValue())
                        .append(CommitLogConstants.CHANGE_SET_LINE_END_SEPARATOR);
            }
            //Изменения этого файла закончены, ставит разделитель и идем дальше
            stringBuilder.append(CommitLogConstants.CHANGE_SET_SEPARATOR).append("\n");
        }
        stringBuilder.append(CommitLogConstants.COMMIT_SEPARATOR).append("\n");


        // Запись в конец файла
        addLog(stringBuilder.toString());
    }

    public void pullCommit() throws IOException {
        String fileText = String.join("\n", Files.readAllLines(commitLogFile));

        String[] commitTextsArray = fileText.split(CommitLogConstants.COMMIT_SEPARATOR);
        String textWithoutLastCommit = String.join(CommitLogConstants.COMMIT_SEPARATOR,
                Arrays.copyOfRange(commitTextsArray, 0, commitTextsArray.length - 1))
                + CommitLogConstants.COMMIT_SEPARATOR;

        FileWriter fileWriter = new FileWriter(commitLogFile.toFile(), false);
        fileWriter.write(textWithoutLastCommit);
        fileWriter.close();
    }

    private void addLog(String string) throws IOException {
        FileWriter fileWriter = new FileWriter(commitLogFile.toFile(), true);

        fileWriter.write(string);
        fileWriter.close();
    }
}
