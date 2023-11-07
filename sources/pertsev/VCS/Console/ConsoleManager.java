package pertsev.VCS.Console;

import java.util.Scanner;

public class ConsoleManager {
    private static String COMMAND_ANNOTATION = "Command: ";
    private Scanner scanner = new Scanner(System.in);

    public String[] getCommand() {
        System.out.print(COMMAND_ANNOTATION);
        return scanner.nextLine().split("\\s");
    }

    public void printResponse(String response) {
        System.out.println(response);
    }
}
