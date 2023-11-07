package pertsev.VCS.Commit;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CommitLogFileWriter {
    private Path commitLogFile;

    public CommitLogFileWriter(Path commitLogFile) {
        this.commitLogFile = commitLogFile;
    }

    public void putCommit(Commit commit) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        //Добавим имя коммита
        stringBuilder.append(commit.getName().trim()).append("\n");

        //Запишем файловую структуру на момент коммита
        for (Path file : commit.getFiles()) {
            stringBuilder.append(file).append("\n");
        }

        //Разделитель
        stringBuilder.append(CommitLogConstants.FILE_STRUCTURE_SEPARATOR).append("\n");

        //Запишем изменения для всех файлов коммита:
        for (Path filePath : commit.getFileChanges().keySet()) {
            //Пропишем путь к файлу
            stringBuilder.append(filePath).append("\n");

            //Запишем каждое изменение этого файла
            List<Change> changes = commit.getFileChanges().get(filePath);
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


    private void addLog(String string) throws IOException {
        FileWriter fileWriter = new FileWriter(commitLogFile.toFile(), true);

        fileWriter.write(string);
        fileWriter.close();
    }
}
