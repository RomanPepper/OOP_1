package pertsev.VCS.FileUtils;

import pertsev.VCS.VCSUtils.Commit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Queue;

public class CommitCollector {
    private Path commitFile;
    private CommitLogFileParser parser;

    public CommitCollector(Path commitFile) {
        this.commitFile = commitFile;
        this.parser = new CommitLogFileParser(commitFile);
    }

    //Собирает текстовое представление указанного файла
    public String collect(String commitName, Path necessaryFile) throws IOException {
        Queue<Commit> commitQueue = parser.readCommitQueue();
        StringBuilder stringBuilder = new StringBuilder();

        for (Commit commit : commitQueue) {
            stringBuilder.append(commit.getName());

        }

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }
}
