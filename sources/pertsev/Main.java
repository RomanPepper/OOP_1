package pertsev;

import pertsev.VCS.VersionControlSystem;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        VersionControlSystem versionControlSystem = new VersionControlSystem();
        versionControlSystem.start();
    }
}
