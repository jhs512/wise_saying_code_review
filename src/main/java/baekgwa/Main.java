package baekgwa;

import static baekgwa.global.database.DataSourceConfig.*;

import baekgwa.wisesaying.controller.WiseSayingController;
import baekgwa.wisesaying.controller.WiseSayingControllerImpl;
import baekgwa.global.data.Commands;
import baekgwa.global.util.ServletUtils;
import baekgwa.global.exception.GlobalExceptionHandlingProxy;
import baekgwa.wisesaying.repository.WiseSayingRepository;
import baekgwa.wisesaying.repository.WiseSayingRepositoryImpl;
import baekgwa.wisesaying.service.WiseSayingService;
import baekgwa.wisesaying.service.WiseSayingServiceImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        //의존성 주입
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        WiseSayingRepository wiseSayingRepository
                = new WiseSayingRepositoryImpl(DB_PATH, LAST_ID_FILE, BUILD_FILE);
        WiseSayingService wiseSayingService
                = new WiseSayingServiceImpl(wiseSayingRepository);
        WiseSayingController wiseSayingController
                = new WiseSayingControllerImpl(bufferedReader, wiseSayingService);

        WiseSayingController wiseSayingControllerProxy
                = GlobalExceptionHandlingProxy.createProxy(wiseSayingController, WiseSayingController.class);

        System.out.println("=== 명언 앱 ==");
        wiseSayingControllerProxy.createDirectories();

        while (true) {
            System.out.print("명령) ");
            String command = bufferedReader.readLine();

            if (command.equals(Commands.REGISTER.getCommand())) {
                wiseSayingControllerProxy.register();
            } else if (command.equals(Commands.EXIT.getCommand())) {
                return;
            } else if (command.startsWith(Commands.DELETE.getCommand())) {
                Long id = ServletUtils.extractId(command, Commands.DELETE.getCommand());
                wiseSayingControllerProxy.delete(id);
            } else if (command.startsWith(Commands.MODIFY.getCommand())) {
                Long id = ServletUtils.extractId(command, Commands.MODIFY.getCommand());
                wiseSayingControllerProxy.modifyWiseSaying(id);
            } else if (command.equals(Commands.BUILD.getCommand())) {
                wiseSayingControllerProxy.build();
            } else if (command.startsWith(Commands.SEARCH.getCommand())) {
                Map<String, String> requestParams = ServletUtils.extractRequestParams(command);
                wiseSayingControllerProxy.search(requestParams);
            }
        }
    }
}