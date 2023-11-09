package pertsev.VCS.Commit;

import pertsev.VCS.File.FileState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeEdit implements Change {
    private final int lineIndex;
    private final String replacementString;

    public ChangeEdit(int lineIndex, String replacementString) {
        this.lineIndex = lineIndex;
        this.replacementString = replacementString;
    }

    @Override
    public void apply(FileState file) {
        if (!file.isExist()) throw new RuntimeException();
        //Строка может быть как изменена (существующая), так и добавлена
        List<String> text = new ArrayList<>(Arrays
                .asList(file.getValue().split("\n")));
        if (lineIndex < text.size()) { //Изменение
            text.set(lineIndex, replacementString);
        } else {
            text.add(replacementString);
        }
        file.setValue(String.join("\n", text));
    }

    @Override
    public String toStringValue() {
        return lineIndex + CommitLogConstants.INDEX_AND_STRING_LINE_SEPARATOR + replacementString;
    }
}
