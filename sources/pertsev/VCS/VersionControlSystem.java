package pertsev.VCS;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionControlSystem {
    private static final String currentDir = "user.dir";
    protected static final Path DEFAULT_TRACKED_DIRECTORY = Paths.get(
            System.getProperty(currentDir) + "/resources");

    private FileTracker fileTracker;
    private FilePatcher filePatcher;
    private ConsoleController controller;

}

