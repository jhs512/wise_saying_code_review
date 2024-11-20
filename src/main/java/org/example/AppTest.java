package org.example;

import org.example.config.Config;
import org.example.controller.WiseSayingController;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.util.TestUtil.*;


public class AppTest {
    public static List<WiseSaying> wiseSayingList = new ArrayList<>();
    public Config config;
    public static void showWiseSayingList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i=wiseSayingList.size()-1; i>=0; i--) {
            WiseSaying ws = wiseSayingList.get(i);
            System.out.println(ws.toString());
        }
    }

//    private final Scanner scanner = new Scanner(System.in);
    private final RequestDispatcher requestDispatcher = new RequestDispatcher();

    private final WiseSayingController wiseSayingController = new WiseSayingController();

    public AppTest(Config config) {
        this.config = config;
    }

    public void integrationTest() {
        System.out.println("== 명언 앱 ==");
        wiseSayingController.postConstruct(config);
        ByteArrayOutputStream outputStream;


        Scanner scanner = genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);

        // ------------- 등록 -----------------
        outputStream = setOutToByteArray();
        String request = scanner.nextLine();
        String content = scanner.nextLine();
        String author = scanner.nextLine();

        WiseSayingDTO model = new WiseSayingDTO(content, author);
        wiseSayingController.addWiseSaying(model);
        clearSetOutToByteArray(outputStream);

        String output = outputStream.toString();

        if(!output.equals("1번 명언이 등록되었습니다.\n")) {
            System.out.println("등록 테스트 실패1");
            return;
        }

        // ------------- 등록 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        content = scanner.nextLine();
        author = scanner.nextLine();

        model = new WiseSayingDTO(content, author);
        wiseSayingController.addWiseSaying(model);
        clearSetOutToByteArray(outputStream);

        output = outputStream.toString();

        if(!output.equals("2번 명언이 등록되었습니다.\n")) {
            System.out.println("등록 테스트 실패2");
            return;
        }

        // ------------- 목록 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
        clearSetOutToByteArray(outputStream);
        output = outputStream.toString();
        if(!output.equals("""
                번호 / 작가 / 명언
                ----------------------
                2 / 작자미상 / 과거에 집착하지 마라.
                1 / 작자미상 / 현재를 사랑하라.
                """)) {
            System.out.println("목록 테스트 실패");
            return;
        }

        // ------------- 삭제?id=1 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
        clearSetOutToByteArray(outputStream);
        output = outputStream.toString();
        if (!output.equals("1번 명언이 삭제되었습니다.\n")) {
            System.out.println("삭제?id=1 실패");
            return;
        }

        // ------------- 삭제?id=2 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
        clearSetOutToByteArray(outputStream);
        output = outputStream.toString();
        if (!output.equals("1번 명언은 존재하지 않습니다.\n")) {
            System.out.println("삭제?id=1 실패");
            return;
        }

        // ------------- 수정?id=3 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
        clearSetOutToByteArray(outputStream);
        output = outputStream.toString();
        if (!output.equals("3번 명언은 존재하지 않습니다.\n")) {
            System.out.println("수정?id=3 실패");
            return;
        }

        // ------------- 수정?id=2 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        int id = Integer.parseInt(request.split("id=")[1]);
        content = scanner.nextLine();
        author = scanner.nextLine();

        model = new WiseSayingDTO(id, content, author);
        wiseSayingController.updateWiseSaying(model);
        clearSetOutToByteArray(outputStream);

        output = outputStream.toString();

        // ------------- 목록 -----------------
        outputStream = setOutToByteArray();
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
        clearSetOutToByteArray(outputStream);
        output = outputStream.toString();
        if(!output.equals("""
                번호 / 작가 / 명언
                ----------------------
                2 / 홍길동 / 현재와 자신을 사랑하라.
                """)) {
            System.out.println("목록 테스트 실패");
            return;
        }
        // ------------- 종료 -----------------
        request = scanner.nextLine();
        requestDispatcher.dispatch(request);
    }



    // ---------------- 단위 테스트 -------------------

    /*
    * 등록 테스트
    * */
    public int addTest() {

        Scanner scanner = genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);
        ByteArrayOutputStream outputStream = setOutToByteArray();

        String request = scanner.nextLine();
        String content = scanner.nextLine();
        String author = scanner.nextLine();

        WiseSayingDTO model = new WiseSayingDTO(content, author);
        wiseSayingController.addWiseSaying(model);
        clearSetOutToByteArray(outputStream);

        String output = outputStream.toString();
        System.out.println(output);
        System.out.println(request);

        if(!request.equals("등록") || !output.equals("1번 명언이 등록되었습니다.\n"))
            return -1;
        return 0;

    }


    public void modifyTest() {
        modifyTest();
    }

    // 삭제
    public void deleteTest() {

    }
}
