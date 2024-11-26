package org.example;

import org.example.mvc.controller.WiseSayingController;
import org.example.mvc.repository.WiseSayingRepository;
import org.example.mvc.repository.WiseSayingRepositoryImpl;
import org.example.mvc.service.WiseSayingService;
import org.example.mvc.service.WiseSayingServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    private final BufferedReader bufferedReader;
    private final WiseSayingRepository wiseSayingRepository;
    private final WiseSayingService wiseSayingService;
    private final WiseSayingController wiseSayingController;

    public App() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        wiseSayingRepository = new WiseSayingRepositoryImpl();
        wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
        wiseSayingController = new WiseSayingController(wiseSayingService, bufferedReader);
    }

    public App(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
        wiseSayingRepository = new WiseSayingRepositoryImpl();
        wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
        wiseSayingController = new WiseSayingController(wiseSayingService, this.bufferedReader);
    }

    public void run() {
        // 디렉토리 없으면 생성

        System.out.println("== 명언 앱 ==");

        try {
            while (true) {
                System.out.print("명령) ");
                String cmd = bufferedReader.readLine();
                if (cmd.equals("종료")) {
                    System.out.println("종료되었습니다.");
                    break;
                } else if (cmd.equals("등록")) {
                    wiseSayingController.registerWiseSaying();
                } else if (cmd.startsWith("목록")) {
                    wiseSayingController.search(cmd);
                } else if (cmd.startsWith("삭제")) {
                    wiseSayingController.deleteWiseSaying(cmd);
                } else if (cmd.startsWith("전체삭제")) {
                    wiseSayingController.deleteAllWiseSaying();
                }else if (cmd.startsWith("수정")) {
                    wiseSayingController.updateWiseSaying(cmd);
                } else if (cmd.equals("빌드")) {
                    wiseSayingController.build();
                } else {
                    System.out.println("유효하지 않은 명령어입니다.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
