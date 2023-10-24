package pertsev.VCS.Commit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Queue;

public class CommitWriter {
    public String collect(Queue<Commit> commitQueue) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

//        for (Commit commit : commitQueue) {
//            stringBuilder.append(commit.getName()).append("\n");
//            for (Path filePath : commit.getFileChanges().keySet()) {
//                stringBuilder.append(filePath).append("\n");
//                ChangeSet changeSet = commit.getFileChanges().get(filePath);
//                Map<ChangeSet.LineShiftPointer, String> changes = changeSet.getChanges();
//                for (ChangeSet.LineShiftPointer lineShiftPointer : changes.keySet()) {
//                    stringBuilder.append(lineShiftPointer.getOldLineIndex()).append(",")
//                            .append(lineShiftPointer.getNewLineIndex()).append("|")
//                            .append(changes.get(lineShiftPointer)).append("\n");
//                }
//                stringBuilder.append(CommitLogConstants.CHANGE_SET_SEPARATOR).append("\n");
//            }
//            stringBuilder.append(CommitLogConstants.COMMIT_SEPARATOR).append("\n");
//        }

        return stringBuilder.toString();
    }
}
