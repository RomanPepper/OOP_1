package pertsev.VCS.FileHandlers;

import pertsev.VCS.Commit.Change;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FileTextComparator implements Comparator<List<String>> {
    @Override
    public int compare(List<String> oldFileRows, List<String> newFileRows) {

        return 0;
    }

    public List<Change> getDiffs(String oldText, String newText) {
        String[] oldTextLines = oldText.split("\n");
        String[] newTextLines = newText.split("\n");

        List<Change> changes = new ArrayList<>();

        for (int i = 0; i < Math.min(oldTextLines.length, newTextLines.length); i++) {
            if (!oldTextLines[i].equals(newTextLines[i])) {

            }
        }
    }
}
