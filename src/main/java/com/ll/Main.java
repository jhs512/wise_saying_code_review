package com.ll;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("== 명언 앱 ==");

        int uniqueid = 0; // 몇번째 명언인지 나타내는 고유 번호
        ArrayList<String> wiseSayings = new ArrayList<>(); // 명언을 저장할 리스트
        ArrayList<String> writers = new ArrayList<>(); // 작가를 저장할 리스트

        while(true){
            System.out.print("명령 )");
            String order = scanner.nextLine();

            if(order.equals("등록")){
                System.out.println("명언 : ");
                String wiseSaying = scanner.nextLine();

                System.out.println("작가 : ");
                String writer = scanner.nextLine();

                uniqueid++;
                wiseSayings.add(wiseSaying); // 명언 저장
                writers.add(writer); // 작가 저장
                System.out.println(uniqueid + " 번 명언이 등록 되었습니다.");
                System.out.println();

            } else if (order.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");

                // 최신 순서대로 출력
                for (int i = wiseSayings.size() - 1; i >= 0; i--) {
                    // 리스트 데이터는 0부터 시작 하지만 명언의 번호는 1부터 시작 해야 한다
                    // wiseSayings.size()는 리스트에 저장된 총 데이터 개수
                    // 가장 마지막 데이터는 size() - 1 번째 인덱스에 저장
                    // *wiseSayings.size() - 1 을 초기값으로 설정한 이유는 리스트의 마지막 데이터를 먼저 처리하기 위해서
                    System.out.println((i + 1) + " / " + writers.get(i) + " / " + wiseSayings.get(i));
                    // i + 1  :  명언의 번호를 1부터 출력하기 위해
                    // 첫 번째 명언: 리스트의 인덱스 0 → 사용자에게는 "1번 명언"으로 출력.
                    // 두 번째 명언: 리스트의 인덱스 1 → 사용자에게는 "2번 명언"으로 출력
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