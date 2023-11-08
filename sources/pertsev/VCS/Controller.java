package pertsev.VCS;

import pertsev.VCS.Commit.Commit;
import pertsev.VCS.Console.ConsoleManager;

import java.io.IOException;
import java.util.Queue;
import java.util.Stack;

public class Controller {
    private enum AppState {
        RUNNING, STOPPED
    }

    private static final String COMMAND_SEPARATOR = "--------------------------------";
    private static final String COMMIT_QUEUE_IS_EMPTY_MESSAGE = "Commit queue is empty";
    private ConsoleManager consoleManager;
    private Repository repository;
    private AppState appState;

    public Controller(Repository repository, ConsoleManager consoleManager) {
        this.repository = repository;
        this.consoleManager = consoleManager;
    }

    protected void start() throws IOException {
        this.appState = AppState.RUNNING;
        while (this.appState == AppState.RUNNING) {
            String[] command = consoleManager.getCommand();
            this.command(command);
        }
    }

    public void command(String[] command) throws IOException {
        switch (command[0]) {
            case "log":
                System.out.println(
                        beautifyCommitLog(repository.getCommitQueue())
                );
                System.out.println(COMMAND_SEPARATOR);
                break;
            case "commit":
                this.repository.commit(command[1]);
                break;
            case "rollback":
//                ...
            case "stop":
                this.appState = AppState.STOPPED;
                break;
            default:
                System.out.println('"' + command[0] + '"' + " is not a command");
        }
    }

    private String beautifyCommitLog(Queue<Commit> commitQueue) {
        if (commitQueue.isEmpty()) return COMMIT_QUEUE_IS_EMPTY_MESSAGE;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Commit queue: ").append("\n");

        Stack<Commit> stack = new Stack<>();

        int i = 1;
        while (!commitQueue.isEmpty()) {
            Commit commit = commitQueue.poll();
            stack.add(commit);
            stringBuilder.append(i).append(". ")
                    .append(commit.name()).append("\n");
            i++;
        }

        while (!stack.isEmpty()) {
            commitQueue.add(stack.pop());
        }

        return stringBuilder.toString();
    }
}
