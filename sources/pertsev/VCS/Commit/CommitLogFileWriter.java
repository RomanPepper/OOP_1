package pertsev.VCS.Commit;

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
        stringBuilder.append(commit.getName()).append("\n");

        //Запишем файловую структуру на момент коммита
        for (String filePath : commit.getFiles()) {
            stringBuilder.append(filePath).append("\n");
        }

        //Разделитель
        stringBuilder.append(CommitLogConstants.FILE_STRUCTURE_SEPARATOR);

        //Запишем изменения для всех файлов коммита:
        for (Path filePath : commit.getFileChanges().keySet()) {
            //Пропишем путь к файлу
            stringBuilder.append(filePath).append("\n");

            //Запишем каждое изменение этого файла
            List<Change> changes = commit.getFileChanges().get(filePath);
            for (Change change : changes) {
                stringBuilder.append(change.toStringValue());
            }
            //Изменения этого файла закончены, ставит разделитель и идем дальше
            stringBuilder.append(CommitLogConstants.CHANGE_SET_SEPARATOR).append("\n");
        }
        stringBuilder.append(CommitLogConstants.COMMIT_SEPARATOR).append("\n");


        // Запись в конец файла
        Files.write(commitLogFile, stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
    }
}
