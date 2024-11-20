
package org.example.mvc;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Controller {

    private final Scanner scanner;
    private final Service service;

    public Controller(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public Controller(Service service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public static void main(String[] args) throws IOException {

        System.out.println("== 명언 앱 ==");

        Service service = new Service(new Repository());
        service.initRepository();

        Controller controller = new Controller(service);
        controller.run();
    }

    public void run() {
        while (true) {
            System.out.print("명령) ");
            try {
                String command = scanner.nextLine();

                if (command.equals("종료")) {
                    break;
                }

                handleCommand(command);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void handleCommand(String command) throws  IOException{
        if (command.equals("등록")) {
            registerWiseSaying();
        } else if (command.startsWith("삭제")) {
            handleDeleteCommand(command);
        } else if (command.startsWith("수정")) {
            handleUpdateCommand(command);
        } else if (command.startsWith("목록")) {
            if(command.equals("목록")) {
                service.getList();
            } else {
                service.searchWiseSayingList(command);
            }
        } else if (command.equals("빌드")) {
            service.build();
            System.out.println("data.json 빌드 완료");
        } else {
            System.out.println("유효하지 않은 명령어입니다.");
        }
    }

    private void registerWiseSaying() throws IOException {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        int register_id = service.register(content, author);

        System.out.println(register_id + "번 명언이 등록되었습니다.");
    }

    private void handleDeleteCommand(String command) {
        try {
            int id = Integer.parseInt(command.substring(6).trim());
            Optional<WiseSaying> optional = service.getWiseSayingById(id);

            if(optional.isPresent()) {
                service.delete(id);
                System.out.println(id + "번 명언 삭제 완료");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 ID 형식입니다.");
        }
    }

    private void handleUpdateCommand(String command) throws IOException {
        try {
            int id = Integer.parseInt(command.substring(6).trim());
            Optional<WiseSaying> optional = service.getWiseSayingById(id);

            if(optional.isPresent()) {
                WiseSaying wiseSaying = optional.get();
                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                String content = scanner.nextLine();
                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                String author = scanner.nextLine();
                service.update(id, content, author);
                System.out.println(id + "번 명언 수정 완료");
            } else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("유효하지 않은 ID 형식입니다.");
        }
    }
}
