package org.example.view;

import org.example.model.WiseSayingItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);
    private static long id = 0L;
    private static final Map<Long, WiseSayingItem> store = new HashMap<>();


    public static void start() {
        System.out.println("== 명언 앱 ==");
        boolean flag = true;

        while (flag) {
            System.out.print("명령) ");
            flag = register(scanner.nextLine());
        }

    }

    private static boolean register(String input) {
        if (input.equals("등록")) {
            System.out.print("명언 : ");
            String wiseSaying = scanner.nextLine();
            System.out.print("작가 : ");
            String author = scanner.nextLine();

            WiseSayingItem item = new WiseSayingItem(++id, author, wiseSaying);
            store.put(item.getId(), item);
            System.out.println(id + "번 명언이 등록되었습니다.");
        } else if (input.equals("종료")) {
            scanner.close();
            return false;
        } else if (input.equals("목록")) {
            System.out.println("번호 / 작가 / 명언");
            System.out.println("---------------");

            for (Map.Entry<Long, WiseSayingItem> entry : store.entrySet()) {
                System.out.println(entry.getKey() + " / "
                        + entry.getValue().getAuthor() + " / "
                        + entry.getValue().getWiseSaying());
            }
        } else if (input.startsWith("삭제?id=")) {
            String id = input.substring(6);
            long parsedId = Long.parseLong(id);

            try {
                if (store.remove(parsedId) == null) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(parsedId + "번 명언은 존재하지 않습니다.");
            }

        }
        return true;
    }
}
