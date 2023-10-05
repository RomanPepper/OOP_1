package pertsev;

//import pertsev.VCS.VersionControlSystem;

import pertsev.VCS.FileUtils.TextDiffComparator;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        VersionControlSystem versionControlSystem = new VersionControlSystem();
//        versionControlSystem.start();

        TextDiffComparator vcsFileComparator = new TextDiffComparator();
        Path file1 = Paths.get(System.getProperty("user.dir") + "/sources/pertsev/test/1.txt");
        Path file2 = Paths.get(System.getProperty("user.dir") + "/sources/pertsev/test/2.txt");

        System.out.println(vcsFileComparator.compare(file1, file2));
    }
}
