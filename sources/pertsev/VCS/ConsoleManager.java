package pertsev.VCS;

import java.util.Scanner;

public class ConsoleManager {
    public String[] getCommand() {
        return new Scanner(System.in).nextLine().split("\s");
    }

    public void printResponse(String response) {
        System.out.println(response);
    }
}
