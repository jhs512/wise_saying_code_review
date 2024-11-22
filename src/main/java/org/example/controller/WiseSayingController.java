package org.example.controller;

import org.example.dto.RequestPageDto;
import org.example.entity.MyConsumer;
import org.example.entity.Page;
import org.example.entity.Pageable;
import org.example.entity.WiseSaying;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WiseSayingController extends Controller {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;


    private static final String WRONG_COMMAND_MESSAGE = "== 잘못된 명령어입니다. ==";
    private static final String NOT_EXISTING_ID_MESSAGE = "번 명언은 존재하지 않습니다.";
    private static final String JSON_FILE_PATH = "data.json";
    private static final String END = "종료";
    private static final String BUILD = "빌드";
    private static final String REGISTER = "등록";
    private static final String LIST = "목록";
    private static final String MODIFY = "수정";
    private static final String DELETE = "삭제";

    private static final String KEYWORDTYPE = "keywordType";
    private static final String KEYWORD = "keyword";
    private static final String PAGE = "page";
    private static final String ID = "id";

    private static final Set<String> commandSet = Set.of(KEYWORDTYPE, KEYWORD, PAGE, ID);

    private static final Map<String, Runnable> PARAMETER_NOT_REQUIRED_COMMANDS_MAP = new HashMap<>();
    private static final Map<String, MyConsumer> PARAMETER_REQUIRED_COMMANDS_MAP = new HashMap<>();
    private static final Map<String, MyConsumer> PARAMETER_OPTIONAL_COMMANDS_MAP = new HashMap<>();

    private static final List<String> PARAMETER_REQUIRED_COMMANDS = List.of(MODIFY, DELETE);
    private static final Pattern PARAMETER_REQUIRED_PATTERN = Pattern.compile("^(" + String.join("|", PARAMETER_REQUIRED_COMMANDS) + ")\\?(\\w+)$");

    private static final List<String> PARAMETER_OPTIONAL_COMMANDS = List.of(LIST);
    private static final Pattern PARAMETER_OPTIONAL_PATTERN = Pattern.compile("^(" + String.join("|", PARAMETER_OPTIONAL_COMMANDS) + ")(\\?\\w)?$");

//아니 하면 할 수록 하드코딩이란 느낌을 지울 수가 없어

    public WiseSayingController(Scanner scanner, String jsonFilePath, String lastIdFilePath){
        this.scanner = scanner;

        wiseSayingService = new WiseSayingService(new WiseSayingRepository(jsonFilePath, lastIdFilePath));

        PARAMETER_NOT_REQUIRED_COMMANDS_MAP.put(
                END, this::flush);

        PARAMETER_NOT_REQUIRED_COMMANDS_MAP.put(
                BUILD, this::build);

        PARAMETER_NOT_REQUIRED_COMMANDS_MAP.put(
                REGISTER, () -> registerNewWiseSaying(scanner));

        PARAMETER_REQUIRED_COMMANDS_MAP.put(
                MODIFY, (id) -> modifyExistingWiseSaying(id, scanner));

        PARAMETER_REQUIRED_COMMANDS_MAP.put(
                DELETE, this::deleteExistingWiseSaying);

        PARAMETER_OPTIONAL_COMMANDS_MAP.put(
            LIST, this::showWiseSayingList);
    }

    private String[] getParams(String input){
        String[] params = new String[3];
        if(input != null && !input.isBlank()){
            String[] parameters = input.split("&");
            System.arraycopy(parameters, 0, params, 0, parameters.length);
        }
        return params;
    }

    private Optional<Integer> getId(String input){
        return input == null ? Optional.empty() : Optional.of(Integer.parseInt(input));
    }

    private void flush(){
        wiseSayingService.flush();
    }

    private void build(){
        flush();
        System.out.printf("%s 파일의 내용이 갱신되었습니다.\n", JSON_FILE_PATH);
    }

    private void showWiseSayingList(String[] params){
        int page = Integer.parseInt(params[0]);
        String keywordTypeText = params[1];
        String keyword = params[2];

        RequestPageDto requestPageDto = new RequestPageDto(keywordTypeText, keyword);
        Page<WiseSaying> wiseSayingPage = new Page<>(new Pageable(page), getWiseSayingPage(requestPageDto));

        for(WiseSaying wiseSaying : wiseSayingPage.getPage()){
            System.out.printf("%d / %s / %s\n",wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }


    private List<WiseSaying> getWiseSayingPage(RequestPageDto requestPageDto) {
        return wiseSayingService.getWiseSayingMap().values().stream()
                .filter(wiseSaying -> {
                    Optional<RequestPageDto.KeywordType> optionalKeywordType = requestPageDto.getKeywordType();
                    Optional<String> optionalKeyword = requestPageDto.getKeyword();

                    if(optionalKeywordType.isEmpty() && optionalKeyword.isEmpty()){
                        return true;
                    }else if(optionalKeywordType.isEmpty() ^ optionalKeyword.isEmpty()) {
                        throw new IllegalStateException();
                    }else{
                        RequestPageDto.KeywordType keywordType = optionalKeywordType.get();
                        String keyword = optionalKeyword.get();

                        if (keywordType == RequestPageDto.KeywordType.AUTHOR) {
                            return wiseSaying.getAuthor().equalsIgnoreCase(keyword);
                        } else if (keywordType == RequestPageDto.KeywordType.CONTENT) {
                            return wiseSaying.getContent().contains(keyword);
                        }

                        return false;
                    }
                }).collect(Collectors.toList());
    }

    private void registerNewWiseSaying(Scanner scanner){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        int lastId = wiseSayingService.registerWiseSaying(new WiseSaying(0, content, author));
        System.out.printf("%d번 명언이 등록되었습니다.\n", lastId);
    }

    private void modifyExistingWiseSaying(String[] params, Scanner scanner){
        Optional<Integer> optionalId = getId(input);
        if(optionalId.isEmpty()){
            throw new IllegalStateException();
        }
        int id = optionalId.get();
        Optional<WiseSaying> optionalWiseSaying = wiseSayingService.getWiseSayingById(id);
        if(optionalWiseSaying.isPresent()){
            WiseSaying existingWiseSaying = optionalWiseSaying.get();
            System.out.printf("명언(기존) : %s\n명언 : ", existingWiseSaying.getContent());
            wiseSayingService.modifyWiseSaying(existingWiseSaying, scanner.nextLine());
        }else {
            System.out.println(id + NOT_EXISTING_ID_MESSAGE);
        }
    }

    private void deleteExistingWiseSaying(String[] idArray){
        int id = Integer.parseInt(idArray[0]);
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

            if(PARAMETER_NOT_REQUIRED_COMMANDS_MAP.containsKey(command)){
                PARAMETER_NOT_REQUIRED_COMMANDS_MAP.get(command).run();
                if(command.equals(END)){
                    break;
                }
            } else{
                Matcher paramOptionalMatcher = PARAMETER_OPTIONAL_PATTERN.matcher(command);
                boolean paramOptionalMatches = paramOptionalMatcher.matches();

                Matcher paramRequiredMatcher = PARAMETER_REQUIRED_PATTERN.matcher(command);
                boolean paramRequiredMatches = paramRequiredMatcher.matches();

                if (paramRequiredMatches || paramOptionalMatches) {
                    String realCommand = paramRequiredMatcher.group(1);
                    String[] params = getParams(paramRequiredMatcher.group(2));

                    //지금은 id=1 이런식으로 바뀌니깐.. 맵으로 바꾸는 것도 필요하겠는걸..
                    PARAMETER_OPTIONAL_COMMANDS_MAP.get(realCommand).accept(params);

                    MyConsumer myConsumer = paramRequiredMatches ?
                            PARAMETER_REQUIRED_COMMANDS_MAP.get(realCommand):
                            PARAMETER_OPTIONAL_COMMANDS_MAP.get(realCommand);
                    myConsumer.accept(params);
                } else {
                    System.out.println("\n" + WRONG_COMMAND_MESSAGE);
                }
            }
            System.out.print("명령)");
        }
        scanner.close();
    }
}
