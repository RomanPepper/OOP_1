package pertsev.VCS.FileUtils;

import java.util.Comparator;
import java.util.List;

public class FileComparator implements Comparator<List<String>> {
    @Override
    public int compare(List<String> oldFileRows, List<String> newFileRows) {

        return 0;
    }

    public String getDiff(List<String> oldFileRows, List<String> newFileRows) {

        for (int i = 0; i < Math.max(oldFileRows.size(), newFileRows.size()); i++) {

        }
    }

    //1 diff - один стринг = одно сравнение одного файла

    public List<String> getDiffList() {

    }
}
