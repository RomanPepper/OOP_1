package pertsev;

import pertsev.VCS.VersionControlSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        VersionControlSystem versionControlSystem = new VersionControlSystem();
        versionControlSystem.launch();
    }
}
