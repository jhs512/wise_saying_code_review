package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import org.example.dto.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("== 명언 앱 ==");
        String cmd;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));




        while (true) {
            System.out.print("명령) ");
            if ((cmd = br.readLine()).equals("종료")) break;  // 종료
            else if (cmd.equals("등록")) {    // 등록
                System.out.print("명언 : ");
                String content = br.readLine();

                System.out.print("작가 : ");
                String author = br.readLine();

                int id = WiseSayingRepository.findLastId();
                WiseSayingService.createJsonFile(id, content, author);
                WiseSayingService.createTxtFile(id);

                System.out.println(id + "번 명언이 등록되었습니다.");

            } else if (cmd.equals("목록")) {      // 목록
                System.out.println("번호 / 작가 / 명언");
                System.out.println("--------------------");

                List<WiseSaying> listOfWiseSaying = WiseSayingService.createListofWiseSaying();
                listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
                for (WiseSaying wiseSaying : listOfWiseSaying) {
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                }

            } else if (cmd.substring(0, 2).equals("삭제")) {  // 삭제

                int tempId = cmd.charAt(6) - '0';
                WiseSayingService.removeJsonFile(tempId);

            } else if (cmd.substring(0, 2).equals("수정")) {  // 수정

                int tempId = cmd.charAt(6) - '0';

                Optional<WiseSaying> wiseSaying = WiseSayingService.getWiseSaying(tempId);
                if (wiseSaying.isPresent()) {
                    // 명언 수정
                    System.out.println("명언(기존) : " + wiseSaying.get().getContent());
                    System.out.print("명언 : ");
                    String content = br.readLine();

                    // 작가 수정
                    System.out.println("작가(기존) : " + wiseSaying.get().getAuthor());
                    System.out.print("작가 : ");
                    String author = br.readLine();

                    WiseSayingService.updateJsonFile(tempId, content, author);
                } else {
                    System.out.println(tempId + "번 명언은 존재하지 않습니다.");
                }

            } else if (cmd.equals("빌드")) {
                WiseSayingService.createBuildFile();
            }
        }

    }
}