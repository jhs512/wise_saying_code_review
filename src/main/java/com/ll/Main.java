package com.ll;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> wiseSayings = new ArrayList<>();
        ArrayList<String> writers = new ArrayList<>();
        int uniqueId = 0;

        while (true) {
            System.out.print("명령 ) ");
            String order = scanner.nextLine().trim();

            if (order.equals("등록")) {
                System.out.print("명언 : ");
                String wiseSaying = scanner.nextLine();

                System.out.print("작가 : ");
                String writer = scanner.nextLine();

                uniqueId++;
                ids.add(uniqueId);
                wiseSayings.add(wiseSaying);
                writers.add(writer);

                System.out.println(uniqueId + " 번 명언이 등록 되었습니다.");
                System.out.println();
            } else if (order.equals("목록")) {
                if (wiseSayings.isEmpty()) {
                    // 명언 목록이 비어 있는 경우를 처리
                    // 명언이 없는 상태에서 "목록" 명령을 입력하면 에러가 발생하지 않고, 명확한 메시지를 출력
                    System.out.println("등록된 명언이 없습니다.");
                } else {
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");
                    // 고유 ID를 사용해 명언 출력
                    // 명언 ID가 항상 고유하도록 관리되기 때문에, 등록 순서와 무관하게 ID를 그대로 출력
                    for (int i = ids.size() - 1; i >= 0; i--) {
                        System.out.println(ids.get(i) + " / " + writers.get(i) + " / " + wiseSayings.get(i));
                    }
                }
                System.out.println();
            } else if (order.startsWith("삭제?id=")) {
                try {
                    // 삭제 명령 처리
                    // 명령에서 `삭제?id=` 부분을 인식하고, 뒤에 있는 ID 값을 추출
                    int idToDelete = Integer.parseInt(order.split("=")[1]);
                    int indexToDelete = ids.indexOf(idToDelete);
                    // ID를 기반으로 실제 데이터의 인덱스를 가져옴

                    if (indexToDelete != -1) {
                        // 삭제 처리
                        // ID가 유효한 경우, 해당 ID와 연관된 데이터를 모두 삭제
                        ids.remove(indexToDelete);
                        wiseSayings.remove(indexToDelete);
                        writers.remove(indexToDelete);

                        System.out.println(idToDelete + "번 명언이 삭제되었습니다.");
                    } else {
                        // ID가 유효하지 않을 경우 에러 메시지 출력
                        System.out.println(idToDelete + "번 명언이 존재하지 않습니다.");
                    }
                } catch (Exception e) {
                    // 잘못된 입력 처리
                    // `삭제?id=` 뒤에 숫자가 없거나 형식이 맞지 않을 경우
                    System.out.println("올바른 삭제 명령을 입력하세요. 예: 삭제?id=1");
                }
                System.out.println();
            } else if (order.startsWith("수정?id=")) {
                try {
                    int idToEdit = Integer.parseInt(order.split("=")[1]);
                    int indexToEdit = ids.indexOf(idToEdit);

                    if (indexToEdit != -1) {
                        // 기존 데이터 출력
                        System.out.println("명언(기존) : " + wiseSayings.get(indexToEdit));
                        System.out.print("명언 : ");
                        String newWiseSaying = scanner.nextLine();

                        System.out.println("작가(기존) : " + writers.get(indexToEdit));
                        System.out.print("작가 : ");
                        String newWriter = scanner.nextLine();

                        // 데이터 업데이트
                        wiseSayings.set(indexToEdit, newWiseSaying);
                        writers.set(indexToEdit, newWriter);

                        System.out.println(idToEdit + "번 명언이 수정되었습니다.");
                    } else {
                        System.out.println(idToEdit + "번 명언은 존재하지 않습니다.");
                    }
                } catch (Exception e) {
                    System.out.println("올바른 수정 명령을 입력하세요. 예: 수정?id=1");
                }
                System.out.println();
            } else if (order.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("알 수 없는 명령입니다.");
                System.out.println();
            }
        }

        scanner.close();
    }
}