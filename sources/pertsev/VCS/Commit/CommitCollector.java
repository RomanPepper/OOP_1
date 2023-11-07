package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;

public class CommitCollector {
    Queue<Commit> commitQueue;
    public CommitCollector(Queue<Commit> commitQueue) {
        this.commitQueue = commitQueue;
    }

    //Собирает текстовое представление указанного файла на момент указанного коммита
    public FileState collect(String commitName, Path necessaryFilePath) throws IOException {
        if (commitQueue.isEmpty()) {
            return null;
        }

        // Начинаем с первого коммита и до нужного нам (невключительно, последний - обработаем отдельно*)
        //обновляем состояние текста к соответствующему коммиту состоянию
        Commit commit = commitQueue.poll();
        FileState file = new FileState(necessaryFilePath, false, null);
        while (!commit.getName().equals(commitName)) {
            updateFileTextToNextCommit(file, commit);
            commit = commitQueue.poll();
        }
        //И после того, как дошли до последнего оставшегося коммита (нужного нам), достраиваем текст и по нему тоже
        updateFileTextToNextCommit(file, commit);

        return file;
    }

    private void updateFileTextToNextCommit(FileState file, Commit nextCommit) {
        List<Change> changes;
        if (nextCommit.getFileChanges().containsKey(file.getPath())) {
            changes = nextCommit.getFileChanges().get(file.getPath());
            for (Change change : changes) {
                change.apply(file);
            }
        }
    }

}
