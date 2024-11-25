package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WiseSayingController {
    private final BufferedReader br;
    public WiseSayingService service;

    public WiseSayingController(BufferedReader br) {
        this.br = br;
        this.service = new WiseSayingService(new WiseSayingRepository());
    }

    public void run() throws IOException {
        System.out.println("== 명언 앱 ==");
        while (true) {
            System.out.print("명령) ");
            String menu = br.readLine();

            if (menu.equals("종료")) {
                break;
            } else if (menu.equals("등록")) {
                registerWiseSaying();
            } else if (menu.equals("목록")) {
                listWiseSayings();
            } else if (menu.equals("삭제")) {
                deleteWiseSaying();
            } else if (menu.equals("수정")) {
                updateWiseSaying();
            } else if (menu.equals("빌드")) {
                buildDataJson();
            } else {
                System.out.println("올바르지 않은 명령입니다.");
            }
        }
    }

    public void registerWiseSaying() throws IOException {
        System.out.print("명언 : ");
        String saying = br.readLine();
        System.out.print("작가 : ");
        String writer = br.readLine();

        int id = service.registerWiseSaying(saying,writer);
        System.out.println(id +"번 명언이 등록되었습니다.");
    }

    public void listWiseSayings() throws IOException {
        System.out.print("page=");
        int page = Integer.parseInt(br.readLine());
        List<WiseSaying> wiseSayings = service.listWiseSayings(page);
        int totalPages = service.getTotalPages();
        System.out.println("-----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-----------------------");
        wiseSayings.forEach(ws ->
                System.out.printf("%d / %s / %s\n", ws.getId(), ws.getWriter(), ws.getSaying()));
        System.out.println("-----------------------");
        System.out.printf("페이지 : %s / %s\n",page == 1? "[1]":"1",page == totalPages?"["+ totalPages+"]": totalPages);
    }

    public void deleteWiseSaying() throws IOException {
        System.out.print("id=");
        int id = Integer.parseInt(br.readLine());
        if (service.deleteWiseSaying(id)) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언이 존재하지 않습니다.");
        }
    }

    public void updateWiseSaying() throws IOException {
        System.out.print("id=");
        int id = Integer.parseInt(br.readLine());
        System.out.print("명언 :");
        String modifiedSaying = br.readLine();
        System.out.print("작가 :");
        String modifiedWriter = br.readLine();

        if (service.updateWiseSaying(id,modifiedSaying,modifiedWriter)){
            System.out.println(id + "번 명언이 수정되었습니다.");
        } else {
            System.out.println(id + "번 명언이 존재하지 않습니다.");
        }
    }

    private void buildDataJson() {
        if (service.buildDataJson()) {
            System.out.println("data.json 파일이 생성되었습니다.");
        } else {
            System.out.println("data.json 파일 생성 중 오류가 발생했습니다.");
        }
    }
}
