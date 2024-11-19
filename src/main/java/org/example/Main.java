package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WiseController controller = new WiseController(new WiseServiceImpl(new WiseRepository()));
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                controller.applyWise();
            } else if (command.equals("목록")) {
                controller.printWise();
            } else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                controller.deleteWise(id);
            } else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                controller.editWise(id);
            } else if (command.equals("빌드")) {
                controller.buildWise();
            }
        }
    }
}