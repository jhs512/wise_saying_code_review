package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WiseManager manager = new WiseManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine();

            if (command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                System.out.print("명언 : ");
                String wise = scanner.nextLine();

                System.out.print("작가 : ");
                String author = scanner.nextLine();

                manager.applyWise(wise, author);
            } else if (command.equals("목록")) {
                manager.printWises();
            } else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                boolean exist = manager.deleteWise(id);

                if (exist) {
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            } else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                Wise previous = manager.findWise(id);

                if (previous != null) {
                    System.out.println("명언(기존) : " + previous.content);
                    System.out.print("명언 : ");
                    String newContent = scanner.nextLine();

                    System.out.println("작가(기존) : " + previous.author);
                    System.out.print("작가 : ");
                    String newAuthor = scanner.nextLine();

                    manager.editWise(id, newContent, newAuthor);
                    previous.content = newContent;
                    previous.author = newAuthor;
                } else {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            }
        }
    }
}