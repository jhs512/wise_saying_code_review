package org.example.controller;

import org.example.object.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiseSayingController extends Controller {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;
    private static final Map<String, Runnable> ID_NOT_REQUIRED_COMMANDS_MAP = new HashMap<>();
    private static final Map<String, Consumer<Integer>> ID_REQUIRED_COMMANDS_MAP = new HashMap<>();

    private static final String WRONG_COMMAND_MESSAGE = "== 잘못된 명령어입니다. ==";
    private static final String NOT_EXISTING_ID_MESSAGE = "번 명언은 존재하지 않습니다.";
    private static final String JSON_FILE_PATH = "data.json";

    private static final List<String> ID_REQUIRED_COMMANDS = List.of("수정", "삭제");
    private static final String END = "종료";
    private static final String LIST = "목록";
    private static final String BUILD = "빌드";
    private static final String REGISTER = "등록";
    private static final String MODIFY = "수정";
    private static final String DELETE = "삭제";

    private static final Pattern PATTERN = Pattern.compile("^(" + String.join("|", ID_REQUIRED_COMMANDS) + ")\\?id=(\\d+)$");


    public WiseSayingController(Scanner scanner, String jsonFilePath, String lastIdFilePath){
        this.scanner = scanner;

        wiseSayingService = new WiseSayingService(new WiseSayingRepository(jsonFilePath, lastIdFilePath));

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                END, this::flush);

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                LIST, this::showWiseSayingList);

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                BUILD, this::build);

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                REGISTER, () -> registerNewWiseSaying(scanner));

        ID_REQUIRED_COMMANDS_MAP.put(
                MODIFY, (id) -> modifyExistingWiseSaying(id, scanner));

        ID_REQUIRED_COMMANDS_MAP.put(
                DELETE, this::deleteExistingWiseSaying);
    }

    private void flush(){
        wiseSayingService.flush();
    }

    private void build(){
        flush();
        System.out.printf("%s 파일의 내용이 갱신되었습니다.\n", JSON_FILE_PATH);
    }

    private void showWiseSayingList(){
        for(WiseSaying wiseSaying : wiseSayingService.getWiseSayingMap().values()){
            System.out.printf("%d / %s / %s\n",wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    private void registerNewWiseSaying(Scanner scanner){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        int lastId = wiseSayingService.registerWiseSaying(new WiseSaying(0, content, author));
        System.out.printf("%d번 명언이 등록되었습니다.\n", lastId);
    }

    private void modifyExistingWiseSaying(int id, Scanner scanner){
        Optional<WiseSaying> optionalWiseSaying = wiseSayingService.getWiseSayingById(id);
        if(optionalWiseSaying.isPresent()){
            WiseSaying existingWiseSaying = optionalWiseSaying.get();
            System.out.printf("명언(기존) : %s\n명언 : ", existingWiseSaying.getContent());
            wiseSayingService.modifyWiseSaying(existingWiseSaying, scanner.nextLine());
        }else {
            System.out.println(id + NOT_EXISTING_ID_MESSAGE);
        }
    }

    private void deleteExistingWiseSaying(int id){
        if(wiseSayingService.deleteWiseSaying(id)){
            System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
        }else {
            System.out.println(id + NOT_EXISTING_ID_MESSAGE);
        }
    }

    public void run(){
        System.out.println("== 명언 앱 ==");
        System.out.print("명령)");

        while(true){
            String command = scanner.nextLine();

            if(ID_NOT_REQUIRED_COMMANDS_MAP.containsKey(command)){
                ID_NOT_REQUIRED_COMMANDS_MAP.get(command).run();
                if(command.equals(END)){
                    break;
                }
            }else {
                Matcher matcher = PATTERN.matcher(command);
                if (matcher.matches()) {
                    String realCommand = matcher.group(1);
                    Integer id = Integer.parseInt(matcher.group(2));
                    ID_REQUIRED_COMMANDS_MAP.get(realCommand).accept(id);
                }else {
                    System.out.println("\n" + WRONG_COMMAND_MESSAGE);
                }
            }
            System.out.print("명령)");
        }
        scanner.close();
    }
}
