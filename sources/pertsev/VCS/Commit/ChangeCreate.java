package pertsev.VCS.Commit;

//Изменение, отражающее создание нового пустого файла
public class ChangeCreate implements Change {
    @Override
    public String toStringValue() {
        return CommitLogConstants.CREATED_FILE_MARKER + "\n";
    }
}
