package pertsev.VCS.Commit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public class CommitCollector {
    private final Path commitFile;
    private CommitLogFileParser parser;

    public CommitCollector(Path commitFile) {
        this.commitFile = commitFile;
        this.parser = new CommitLogFileParser(commitFile);
    }

    //Собирает текстовое представление указанного файла
    public String collect(String commitName, Path necessaryFile) throws IOException {
        Queue<Commit> commitQueue = parser.readCommitQueue();

        if (commitQueue.isEmpty()) {
            return "";
        }

        //Начинаем с первого коммита и до нужного нам (невключительно, последний - обработаем отдельно)
        Commit commit = commitQueue.poll();
        String text = "";
        while (!commit.getName().equals(commitName)) {
            text = updateFileTextToNextCommit(text, commit, necessaryFile);
            commit = commitQueue.poll();
        }
        //И после того, как дошли до последнего оставшегося коммита (нужного нам), достраиваем текст и по нему тоже
        text = updateFileTextToNextCommit(text, commit, necessaryFile);


        return text;
    }

    private String updateFileTextToNextCommit(String textToUpdate, Commit nextCommit, Path filePath) {
        String[] linedText = textToUpdate.split("\n");
        for (Change change : nextCommit.getFileChanges().get(filePath)) {
            Change.LineShiftPointer lineShiftPointer = change.getLineShiftPointer();
            String replacementString = change.getReplacementString();

            linedText[lineShiftPointer.getOldLineIndex()] = replacementString;
        }

        return String.join("\n", linedText);
    }
}
