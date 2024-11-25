package org.example.controller;

import org.example.common.Command;
import org.example.common.Parameter;
import org.example.pagination.Page;
import org.example.entity.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;
import java.util.*;
import java.util.function.Consumer;

public class WiseSayingController extends Controller {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;
    private final String jsonFilePath;

    private static final String WRONG_COMMAND_MESSAGE = "== 잘못된 명령어입니다. ==";
    private static final String NOT_EXISTING_ID_MESSAGE = "번 명언은 존재하지 않습니다.";

    private static final Map<Command, Consumer<Map<Parameter, String>>> COMMANDS_MAP = new HashMap<>();

    public WiseSayingController(Scanner scanner, String jsonFilePath, String lastIdFilePath) {
        this.scanner = scanner;
        this.jsonFilePath = jsonFilePath;

        wiseSayingService = new WiseSayingService(new WiseSayingRepository(jsonFilePath, lastIdFilePath));

        COMMANDS_MAP.put(
                Command.BUILD, this::build
        );
        COMMANDS_MAP.put(
                Command.END, this::end
        );
        COMMANDS_MAP.put(
                Command.REGISTER, (params) -> registerNewWiseSaying(scanner, params)
        );
        COMMANDS_MAP.put(
                Command.LIST, this::showWiseSayingList
        );
        COMMANDS_MAP.put(
                Command.MODIFY, (params) -> modifyExistingWiseSaying(scanner, params)
        );
        COMMANDS_MAP.put(
                Command.DELETE, this::deleteExistingWiseSaying
        );
    }

    private Command getCommand(String input){
        return Command.fromDescription(input.split("\\?", 2)[0]);
    }

    private Map<Parameter, String> getParams(String input){
        Map<Parameter, String> params = new HashMap<>();
        String[] inputArray = input.split("\\?", 2);
        if(inputArray.length != 1){
            String[] parameters = inputArray[1].split("&");
            for(String parameter : parameters){
                String[] keyAndValue = parameter.split("=", 2);
                if(keyAndValue.length != 2){
                    throw new IllegalArgumentException();
                }else {
                    String key = keyAndValue[0];
                    String value = keyAndValue[1];
                    params.put(Parameter.fromName(key), value);
                }
            }
        }
        return params;
    }


    private void end(Map<Parameter, String> params){
        build(params);
        System.exit(0);
    }

    private void build(Map<Parameter, String> params){
        wiseSayingService.flush();
        System.out.printf("%s 파일의 내용이 갱신되었습니다.\n", jsonFilePath);
    }

    private void showWiseSayingList(Map<Parameter, String> params){
        Page<WiseSaying> wiseSayingPage = wiseSayingService.getWiseSayingPage(params);
        for(WiseSaying wiseSaying : wiseSayingPage.getPage()){
            System.out.printf("%d / %s / %s\n", wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
        StringBuilder sb = new StringBuilder();
        int totalPage = wiseSayingPage.getTotalPageNum();
        for(int i = 1; i <= totalPage; i++){
            if(i == wiseSayingPage.getPageNum()){
                sb.append("[").append(i).append("]");
            }else {
                sb.append(i);
            }
            if(i != totalPage){
                sb.append("/");
            }
        }
        System.out.println(sb);
    }


    private void registerNewWiseSaying(Scanner scanner, Map<Parameter, String> params){
        if(!params.isEmpty()){
            throw new IllegalStateException();
        }
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        int lastId = wiseSayingService.registerWiseSaying(new WiseSaying(0, content, author));
        System.out.printf("%d번 명언이 등록되었습니다.\n", lastId);
    }

    private void modifyExistingWiseSaying(Scanner scanner, Map<Parameter, String> params){
        try {
            int updatedId = wiseSayingService.modifyWiseSaying(scanner, params);
            if(updatedId <= 0){
                System.out.println(-updatedId + NOT_EXISTING_ID_MESSAGE);
            }
        }catch (NumberFormatException e){
            System.out.println(WRONG_COMMAND_MESSAGE);
        }
    }

    private void deleteExistingWiseSaying(Map<Parameter, String> params){
        try {
            int deletedId = wiseSayingService.deleteWiseSaying(params);
            if(deletedId <= 0){
                System.out.println(-deletedId + NOT_EXISTING_ID_MESSAGE);
            }else {
                System.out.printf("%d번 명언이 삭제되었습니다.\n",deletedId);
            }
        }catch (NumberFormatException e){
            System.out.println(WRONG_COMMAND_MESSAGE);
        }
    }

    public void run(){
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령)");
            String input = scanner.nextLine();
            try{
                Command command = getCommand(input);
                Map<Parameter, String> params = getParams(input);
                COMMANDS_MAP.get(command).accept(params);
                if(command.equals(Command.END)){
                    break;
                }
            }catch (IllegalArgumentException e){
                System.out.println(WRONG_COMMAND_MESSAGE);
            }
        }
        scanner.close();
    }
}
