package pertsev.VCS;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final Path COMMITS_FILE = Paths.get(PROJECT_DIR + "/sources/commit_log.txt");
    private static final String COMMAND_SEPARATOR = "\n --------------------------------";
    private Repository repository;
    private Controllable vcs;

    public Controller(Controllable vcs, Repository repository) {
        this.repository = repository;
        this.vcs = vcs;
        List<Integer> list = new ArrayList<>();
        list.forEach(System.out::println);
    }

    public String command(String[] command) throws FileNotFoundException {
        switch (command[0]) {
            case "status":
//                return fileTracker.status() + COMMAND_SEPARATOR;
                return COMMAND_SEPARATOR;
            case "log":
                return readLogFile();
            default:
                return '"' + command[0] + '"' + " is not a command";
        }
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
