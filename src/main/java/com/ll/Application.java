package com.ll;

import java.util.*;

enum Command {
    CREATE,
    LIST_ALL,
    DELETE,
    UPDATE,
    BUILD,
    EXIT,
    SEARCH
}

class Console {

    static Scanner scanner = new Scanner(System.in);
    static String args;

    public static Command getCommand() {
        while (true) {
            Console.print("명령) ");
            String command = getInput();

            try {
                args = command.split("\\?")[1];
            } catch (IndexOutOfBoundsException e) {
                args = "";
            }

            if (Objects.equals(command, "등록")) return Command.CREATE;
            if (command.contains("삭제")) return Command.DELETE;
            if (Objects.equals(command, "목록")) return Command.LIST_ALL;
            if (command.contains("수정")) return Command.UPDATE;
            if (Objects.equals(command, "빌드")) return Command.BUILD;
            if (Objects.equals(command, "종료")) return Command.EXIT;
        }
    }

    public static String getArgs() {
        return args;
    }

    public static String getInput() {
        return scanner.nextLine().trim();
    }

    public static void printWelcome() {
        System.out.println("== 명언 앱 ==");
    }

    public static void print(String args) {
        System.out.print(args);
    }
}

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.execute();
    }
}




