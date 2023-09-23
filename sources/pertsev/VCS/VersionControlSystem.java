package pertsev.VCS;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionControlSystem {
    private static final String projectDir = "user.dir";
    private static final Path DEFAULT_REPOSITORY_DIRECTORY = Paths.get(
            System.getProperty(projectDir) + "/resources");
    private ConsoleManager consoleManager = new ConsoleManager();
    private Controller controller = new Controller(DEFAULT_REPOSITORY_DIRECTORY);
    private Repository repository;

    public VersionControlSystem() {
        this.repository = new Repository(DEFAULT_REPOSITORY_DIRECTORY);
    }

    public VersionControlSystem(Path repositoryPath) {
        this.repository = new Repository(repositoryPath);
    }

    public void start() {
        while (true) {
            String[] command = consoleManager.getCommand();
            String response = controller.command(command);
            consoleManager.printResponse(response);
        }
    }
}