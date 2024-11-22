package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.example.controller.WiseSayingController;
import org.example.util.DependencyContainer;

public class Main {

    public static void main(String[] args) throws IOException {
        App app = new App();
        app.run();
    }

    public static class App {
        private final BufferedReader br;
        private final WiseSayingController wiseSayingController;
        DependencyContainer container = new DependencyContainer();

        public App() {
            br = new BufferedReader(new InputStreamReader(System.in));
            this.wiseSayingController = container.createWiseSayingController();
        }

        public App(BufferedReader br) {
            this.br = br;
            this.wiseSayingController = container.createWiseSayingController();
        }

        public void run() throws IOException {
            System.out.println("== 명언 앱 ==");
            String cmd;

            while (true) {
                System.out.print("명령) ");
                cmd = br.readLine();

                if (cmd.equals("종료")) {
                    System.out.println("앱이 종료 되었습니다.");
                    break;
                } else if (cmd.equals("등록")) {
                    wiseSayingController.createWiseSaying(br);
                } else if (cmd.startsWith("목록")) {
                    if (cmd.startsWith("목록?keywordType")) {
                        wiseSayingController.getListByKeyword(cmd);
                    } else {
                        wiseSayingController.getAllWiseSaying(cmd);
                    }
                }  else if (cmd.startsWith("삭제")) {
                    wiseSayingController.deleteWiseSaying(cmd);
                } else if (cmd.startsWith("수정")) {
                    wiseSayingController.updateWiseSaying(cmd, br);
                } else if (cmd.equals("빌드")) {
                    wiseSayingController.createBuildFile();
                } else {
                    System.out.println("올바르지 않은 명령어 입니다.");
                }
            }

        }
    }
}