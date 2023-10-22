package pertsev.VCS.Console;

import java.util.Scanner;

public class ConsoleManager {
    private Scanner scanner = new Scanner(System.in);

    public String[] getCommand() {
        return scanner.nextLine().split("\\s");
    }

    public void printResponse(String response) {
        System.out.println(response);
    }
}
