package pertsev.VCS;

import pertsev.VCS.Console.ConsoleManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Controller {
    private enum APP_STATE {
        RUNNING, STOPPED
    }

    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    private static final String COMMAND_SEPARATOR = "\n --------------------------------";
    private ConsoleManager consoleManager;
    private Repository repository;

    private APP_STATE appState;

    public Controller(Repository repository, ConsoleManager consoleManager) {
        this.repository = repository;
        this.consoleManager = consoleManager;
    }

    protected void start() throws FileNotFoundException {
        this.appState = APP_STATE.RUNNING;
        while (this.appState == APP_STATE.RUNNING) {
            String[] command = consoleManager.getCommand();
            this.command(command);
        }
    }

    public void command(String[] command) throws FileNotFoundException {
//        switch (command[0]) {
//            case "status":
//                return fileTracker.status() + COMMAND_SEPARATOR;
//            case "log":
//                return readLogFile();
//            default:
//                return '"' + command[0] + '"' + " is not a command";
//        }
    }

    private String readLogFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(COMMITS_FILE.toFile()));
        StringBuilder stringBuilder = new StringBuilder();

        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }

        return stringBuilder.toString();
    }
}
