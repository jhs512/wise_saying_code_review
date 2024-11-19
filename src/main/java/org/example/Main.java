package org.example;//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String WRONG_COMMAND_MESSAGE = "== 잘못된 명령어입니다. ==";
    private static final String NOT_EXISTING_ID_MESSAGE = "번 명언은 존재하지 않습니다.";
    private static final String JSON_FILE_PATH = "data.json";
    private static final String LAST_ID_FILE_PATH = "lastId.txt";

    private static final List<String> ID_REQUIRED_COMMANDS = List.of("수정", "삭제");
    private static final String END = "종료";
    private static final String LIST = "목록";
    private static final String BUILD = "빌드";
    private static final String REGISTER = "등록";
    private static final String MODIFY = "수정";
    private static final String DELETE = "삭제";

    private static final Pattern PATTERN = Pattern.compile("^(" + String.join("|", ID_REQUIRED_COMMANDS) + ")\\?id=(\\d+)$");
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Map<String, Runnable> ID_NOT_REQUIRED_COMMANDS_MAP = new HashMap<>();
    private static final Map<String, Consumer<Integer>> ID_REQUIRED_COMMANDS_MAP = new HashMap<>();

    private void setCommandMap(Scanner scanner, Map<Integer, WiseSaying> wiseSayingMap, List<WiseSaying> wiseSayingList, Integer lastId){
        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                END, () -> end(wiseSayingList, lastId));

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                LIST, () -> showWiseSayingList(wiseSayingList));

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                BUILD, () -> build(wiseSayingList, lastId));

        ID_NOT_REQUIRED_COMMANDS_MAP.put(
                REGISTER, () -> registerNewWiseSaying(scanner, wiseSayingMap, wiseSayingList, lastId));



        ID_REQUIRED_COMMANDS_MAP.put(
                MODIFY, (id) -> modifyExistingWiseSaying(wiseSayingMap, id, scanner));

        ID_REQUIRED_COMMANDS_MAP.put(
                DELETE, (id) -> deleteExistingWiseSaying(wiseSayingMap, id, wiseSayingList));
    }

    private String errorMessage(Exception e){
        return ("error occurred. message :" + e.getMessage());
    }

    private void end(List<WiseSaying> wiseSayingList, int lastId){
        try {
            File jsonFile = new File(JSON_FILE_PATH);
            MAPPER.writeValue(jsonFile, wiseSayingList);

            File lastIdFile = new File(LAST_ID_FILE_PATH);
            BufferedWriter writer = new BufferedWriter(new FileWriter(lastIdFile, false));
            writer.write(String.valueOf(lastId));
            writer.close();
        }catch (IOException e){
            System.out.println(errorMessage(e));
        }
    }

    private void build(List<WiseSaying> wiseSayingList, int lastId){
        end(wiseSayingList, lastId);
        System.out.printf("%s 파일의 내용이 갱신되었습니다.\n", JSON_FILE_PATH);
    }

    private void showWiseSayingList(List<WiseSaying> wiseSayingList){
        for(WiseSaying wiseSaying : wiseSayingList){
            System.out.printf("%d / %s / %s\n",wiseSaying.getId(), wiseSaying.getAuthor(), wiseSaying.getContent());
        }
    }

    private void registerNewWiseSaying(Scanner scanner, Map<Integer, WiseSaying> wiseSayingMap, List<WiseSaying> wiseSayingList, Integer lastId){
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();
        WiseSaying newWiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayingList.add(newWiseSaying);
        wiseSayingMap.put(lastId, newWiseSaying);
        System.out.printf("%d번 명언이 등록되었습니다.\n", lastId);
    }

    private void modifyExistingWiseSaying(Map<Integer, WiseSaying> wiseSayingMap, int id, Scanner scanner){
        WiseSaying existingWiseSaying = wiseSayingMap.get(id);
        System.out.printf("명언(기존) : %s\n명언 : ", existingWiseSaying.getContent());
        existingWiseSaying.setContent(scanner.nextLine());
    }

    private void deleteExistingWiseSaying(Map<Integer, WiseSaying> wiseSayingMap, int id, List<WiseSaying> wiseSayingList){
        WiseSaying existingWiseSaying = wiseSayingMap.get(id);
        wiseSayingMap.remove(id);
        wiseSayingList.remove(existingWiseSaying);
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    public List<WiseSaying> getWiseSayingList(Map<Integer, WiseSaying> wiseSayingMap){
        try{
            File file = new File(JSON_FILE_PATH);
            if(file.createNewFile() || file.length() == 0){
                return new ArrayList<>();
            }else {
                List<WiseSaying> wiseSayingList = MAPPER.readValue(file, new TypeReference<>() {});
                for(WiseSaying wiseSaying : wiseSayingList){
                    wiseSayingMap.put(wiseSaying.getId(), wiseSaying);
                }
                return wiseSayingList;
            }
        }catch (IOException e){
            System.out.println(errorMessage(e));
            return new ArrayList<>();
        }
    }

    public int getLastId(){
        try{
            File file = new File(LAST_ID_FILE_PATH);
            if(file.createNewFile()){
                return 0;
            }else{
                try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                    return Integer.parseInt(reader.readLine());
                }catch (IOException e){
                    return 0;
                }catch (NumberFormatException e){
                    System.out.println(errorMessage(e));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
                    writer.write(String.valueOf(0));
                    writer.close();
                    return 0;
                }
            }
        }catch (IOException e){
            System.out.println(errorMessage(e));
            return 0;
        }
    }


    private void start(Scanner scanner, Map<Integer, WiseSaying> wiseSayingMap, List<WiseSaying> wiseSayingList, int lastId){
        setCommandMap(scanner, wiseSayingMap, wiseSayingList, lastId);
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
                    Integer id = Integer.parseInt(matcher.group(2));
                    if(!wiseSayingMap.containsKey(id)) {
                        System.out.println(id + NOT_EXISTING_ID_MESSAGE);
                    }else{
                        ID_REQUIRED_COMMANDS_MAP.get(command).accept(id);
                    }
                }else {
                    System.out.println("\n" + WRONG_COMMAND_MESSAGE);
                }
            }
            System.out.print("명령)");
        }
        scanner.close();
    }


    public static void main(String[] args) {
        Main main = new Main();

        Scanner scanner = new Scanner(System.in);
        Map<Integer, WiseSaying> wiseSayingMap = new HashMap<>();
        List<WiseSaying> wiseSayingList = main.getWiseSayingList(wiseSayingMap);
        int lastId = main.getLastId();

        main.start(scanner, wiseSayingMap, wiseSayingList, lastId);
    }
}