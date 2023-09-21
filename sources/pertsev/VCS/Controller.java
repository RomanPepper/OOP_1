package pertsev.VCS;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {
    private static final String COMMAND_SEPARATOR = "\n --------------------------------";
    private FileTracker fileTracker;
    private FilePatcher filePatcher;

    public Controller(Path trackedDirectory) {
        this.fileTracker = new FileTracker(trackedDirectory);
        this.filePatcher = new FilePatcher(trackedDirectory);
    }

    public String command(String[] command) {
        switch (command[0]) {
            case "status":
                return fileTracker.status() + COMMAND_SEPARATOR;
            case "repository":
                Path trackedDirectory = Paths.get(command[1]);
                this.setTrackedDirectory(trackedDirectory);
                return "new repository: " + trackedDirectory.toUri() + COMMAND_SEPARATOR;
            case ""
            default:
                return '"' + command[0] + '"' + " is not a command";
        }
        return "";
    }

    private void setTrackedDirectory(Path directory) {
        this.fileTracker.setTrackedDirectory(directory);
        this.filePatcher.setTrackedDirectory(directory);
    }
}
