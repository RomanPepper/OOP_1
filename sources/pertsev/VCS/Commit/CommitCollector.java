package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

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
        Stack<Commit> stack = new Stack<>();

        Commit commit = commitQueue.poll();
        stack.push(commit);

        FileState file = new FileState(necessaryFilePath, false, null);

        while (!commit.name().equals(commitName)) {
            updateFileTextToNextCommit(file, commit);

            commit = commitQueue.poll();
            stack.push(commit);
        }
        //И после того, как дошли до последнего оставшегося коммита (нужного нам), достраиваем текст и по нему тоже
        updateFileTextToNextCommit(file, commit);

        //Вернем коммиты на свои места
        while (!commitQueue.isEmpty()) {
            stack.push(commitQueue.poll());
        }
        while (!stack.isEmpty()) {
            commitQueue.add(stack.pop());
        }

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
