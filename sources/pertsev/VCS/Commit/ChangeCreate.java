package pertsev.VCS.Commit;


import java.nio.file.Path;

//Изменение, отражающее создание нового пустого файла
public class ChangeCreate implements Change {
    @Override
    public FileValueWrapper apply(String[] linedText, Path path) {
        return new CreatedFileValueWrapper();
    }

    @Override
    public String toStringValue() {
        return CommitLogConstants.CREATED_FILE_MARKER + "\n";
    }
}
