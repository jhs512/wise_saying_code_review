package org.example;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int index = 1;
        ArrayList<Wise> wises = new ArrayList<>();

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

                wises.add(new Wise(index, author, wise));
                System.out.println(index++ + "번 명언이 등록되었습니다.");
            } else if (command.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("---------------");
                wises.forEach(wise -> System.out.println(wise));
            } else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                boolean exist = wises.removeIf(wise -> wise.index == id);

                if (exist) {
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            } else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.split("\\?id=")[1]);
                Optional<Wise> target = wises.stream().filter(wise -> wise.index == id).findFirst();

                target.ifPresentOrElse(wise -> {
                    System.out.println("명언(기존) : " + wise.wise);
                    System.out.print("명언 : ");
                    String newWise = scanner.nextLine();

                    System.out.println("작가(기존) : " + wise.author);
                    System.out.print("작가 : ");
                    String newAuthor = scanner.nextLine();

                    wise.author = newAuthor;
                    wise.wise = newWise;
                }, () -> {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                });
            }
        }
    }
}