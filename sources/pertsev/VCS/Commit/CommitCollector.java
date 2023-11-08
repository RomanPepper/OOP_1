package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CommitCollector {
    CommitQueue commitQueue;

    public CommitCollector(CommitQueue commitQueue) {
        this.commitQueue = commitQueue;
    }

    //Собирает текстовое представление указанного файла на момент указанного коммита
    public FileState collect(String commitName, Path necessaryFilePath) {
        if (commitQueue.isEmpty()) {
            return null;
        }

        // Начинаем с первого коммита и до нужного нам (невключительно, последний - обработаем отдельно*)
        //обновляем состояние текста к соответствующему коммиту состоянию
        Queue<Commit> buffer = new LinkedList<>(); //Стек, накапливающий обрабатываемые коммиты

        Commit commit = commitQueue.poll();
        buffer.add(commit);

        FileState file = new FileState(necessaryFilePath, false, null);
        while (!commit.name().equals(commitName)) {
            if (commitQueue.isEmpty()) throw new RuntimeException();

            updateFileTextToNextCommit(file, commit);

            commit = commitQueue.poll();
            buffer.add(commit);
        }
        //И после того, как дошли до последнего оставшегося коммита (нужного нам), достраиваем текст и по нему тоже
        updateFileTextToNextCommit(file, commit);

        //Переливаем оставшиеся коммиты обратно в очередь
        while (!commitQueue.isEmpty())
            buffer.add(commitQueue.poll());

        while (!buffer.isEmpty())
            commitQueue.add(buffer.poll());

        return file;
    }

    private void updateFileTextToNextCommit(FileState file, Commit nextCommit) {
        List<Change> changes;
        if (nextCommit.fileChanges().containsKey(file.getPath())) {
            changes = nextCommit.fileChanges().get(file.getPath());
            for (Change change : changes) {
                change.apply(file);
            }
        }
    }

}
