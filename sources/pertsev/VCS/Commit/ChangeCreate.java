package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

//Изменение, отражающее создание нового пустого файла
public class ChangeCreate implements Change {
    @Override
    public void apply(FileState file) {
        file.setExist(true);
        file.setValue("");
    }

    @Override
    public String toStringValue() {
        return CommitLogConstants.CREATED_FILE_MARKER;
    }
}
