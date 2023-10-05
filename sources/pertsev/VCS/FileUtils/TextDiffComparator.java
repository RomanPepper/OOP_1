package pertsev.VCS.FileUtils;

import java.util.Comparator;
import java.util.List;

public class TextDiffComparator implements Comparator<List<String>> {
    @Override
    public int compare(List<String> oldFileRows, List<String> newFileRows) {
        for (int i = 0; i < Math.max(oldFileRows.size(), newFileRows.size()); i++) {
            System.out.println(i);
        }
    }
}
