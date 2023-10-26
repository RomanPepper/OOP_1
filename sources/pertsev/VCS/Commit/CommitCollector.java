package pertsev.VCS.Commit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;

public class CommitCollector {
    private CommitLogFileParser parser;

    public CommitCollector(Path commitFile) {
        this.parser = new CommitLogFileParser(commitFile);
    }

    //Собирает текстовое представление указанного файла на момент указанного коммита
    public FileValueWrapper collect(String commitName, Path necessaryFilePath) throws IOException {
        Queue<Commit> commitQueue = parser.readCommitQueue();

        if (commitQueue.isEmpty()) {
            return null;
        }

        // Начинаем с первого коммита и до нужного нам (невключительно, последний - обработаем отдельно*)
        //обновляем состояние текста к соответствующему коммиту состоянию
        Commit commit = commitQueue.poll();
        FileValueWrapper fileWrapper;
        String[] linedText = new String[0];
        while (!commit.getName().equals(commitName)) {
            FileValueWrapper fileValueWrapper = updateFileTextToNextCommit(linedText, commit, necessaryFilePath);
            if (fileValueWrapper.getType() == FileValueWrapper.Type.CREATE) {

            }
            linedText = updateFileTextToNextCommit(linedText, commit, necessaryFilePath)
                    .getValue().split("\n");
            commit = commitQueue.poll();
        }
        //И после того, как дошли до последнего оставшегося коммита (нужного нам), достраиваем текст и по нему тоже
        fileWrapper = updateFileTextToNextCommit(linedText, commit, necessaryFilePath);

        return fileWrapper;
    }

    private FileValueWrapper updateFileTextToNextCommit(String[] linedText, Commit nextCommit, Path filePath) {
        List<Change> changes;
        if (nextCommit.getFileChanges().containsKey(filePath)) {
            changes = nextCommit.getFileChanges().get(filePath);
        } else {
            return new CreatedFileValueWrapper();
        }
        for (Change change : changes) {
            return change.apply(linedText, filePath);
        }
        return null;
    }

}
