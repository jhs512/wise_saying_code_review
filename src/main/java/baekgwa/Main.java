package baekgwa;

import baekgwa.controller.WiseSayingController;
import baekgwa.global.Commands;
import baekgwa.global.Utils;
import baekgwa.repository.WiseSayingRepository;
import baekgwa.repository.WiseSayingRepositoryImpl;
import baekgwa.service.WiseSayingService;
import baekgwa.service.WiseSayingServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        //의존성 주입
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        WiseSayingRepository wiseSayingRepository = new WiseSayingRepositoryImpl();
        WiseSayingService wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
        WiseSayingController wiseSayingController = new WiseSayingController(bufferedReader,
                wiseSayingService);

        System.out.println("=== 명언 앱 ==");
        try {
            wiseSayingController.createDirectories();
        } catch (IOException e) {
            System.out.println("폴더 생성 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }

        while (true) {
            System.out.print("명령) ");
            String command = bufferedReader.readLine();

            if (command.equals(Commands.REGISTER.getCommand())) {
                wiseSayingController.register();
            } else if (command.equals(Commands.EXIT.getCommand())) {
                return;
                //목록 조회 기능, 페이지네이션으로 변경 처리.
//            } else if (command.equals(Commands.LIST.getCommand())) {
//                wiseSayingController.printAll();
            } else if (command.startsWith(Commands.DELETE.getCommand())) {
                Long id = Utils.extractId(command, Commands.DELETE.getCommand());
                wiseSayingController.delete(id);
            } else if (command.startsWith(Commands.MODIFY.getCommand())) {
                Long id = Utils.extractId(command, Commands.MODIFY.getCommand());
                wiseSayingController.modifyWiseSaying(id);
            } else if (command.equals(Commands.BUILD.getCommand())) {
                wiseSayingController.build();
            } else if (command.startsWith(Commands.SEARCH.getCommand())) {
                Map<String, String> orders = Utils.extractKeyword(command);
                wiseSayingController.search(orders);
            }
        }
    }
}