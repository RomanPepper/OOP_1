package pertsev.VCS;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionControlSystem {
    private static final String currentDir = "user.dir";
    private static final Path DEFAULT_TRACKED_DIRECTORY = Paths.get(
            System.getProperty(currentDir) + "/resources");

    private ConsoleManager consoleManager = new ConsoleManager();
    private Controller controller = new Controller(DEFAULT_TRACKED_DIRECTORY);

    public void start() {
        while (true) {
            String[] command = consoleManager.getCommand();
            String response = controller.command(command);
            consoleManager.printResponse(response);
        }
    }
}