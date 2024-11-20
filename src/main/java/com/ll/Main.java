package com.ll;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Command {
    CREATE,
    LIST_ALL,
    DELETE,
    UPDATE,
    BUILD,
    EXIT;
}

class Console {

    static Scanner scanner = new Scanner(System.in);
    static String args;

    public static Command getCommand() {
        while (true) {
            Console.print("명령) ");
            String command = getInput();

            try {
                args = command.split("\\?")[1];
            } catch (IndexOutOfBoundsException e) {
                args = "";
            }

            if (Objects.equals(command, "등록")) return Command.CREATE;
            if (command.contains("삭제")) return Command.DELETE;
            if (Objects.equals(command, "목록")) return Command.LIST_ALL;
            if (command.contains("수정")) return Command.UPDATE;
            if (Objects.equals(command, "빌드")) return Command.BUILD;
            if (Objects.equals(command, "종료")) return Command.EXIT;
        }
    }

    public static String getArgs() {
        return args;
    }

    public static String getInput() {
        return scanner.nextLine().trim();
    }

    public static void printWelcome() {
        System.out.println("== 명언 앱 ==");
    }

    public static void print(String args) {
        System.out.print(args);
    }

    public static Map<String, String> getParsedArgs(String input) {
        try {
            String args = input.split("\\?")[1];
            String key = args.split("=")[0];
            String value = args.split("=")[1];

            Map<String, String> result = new HashMap<>();
            result.put(key, value);
            return result;

        } catch (IndexOutOfBoundsException e) {
            return new HashMap<>();
        }
    }
}

class Controller {
    static String FILE_DIR = "src/main/resources/db/wiseSaying";
    public List<Map<String, String>> data;
    public Boolean isTestMode;

    Controller(Boolean isTestMode) {
        data = load();
        this.isTestMode = isTestMode;
    }

    Controller() {
        this(false);
    }

    private File[] getFiles() {
        File directory = new File(FILE_DIR);

        if (directory.exists() && directory.isDirectory()) {
            return directory.listFiles();
        } else {
            return new File[0];
        }
    }

    private StringBuilder loadStringFile() throws IOException{
        StringBuilder data = new StringBuilder();

        data.append("[");
        for (File file : getFiles()) {
            if (file.getName().equals("data.json")) continue; //data.json 패스
            if (file.getName().equals("lastId.txt")) continue; //lastId 패스

            try (Scanner scanner = new Scanner(new File(STR."\{FILE_DIR}/\{file.getName()}"))){
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    data.append(line);
                    data.append(",");
                }

            } catch (IOException e) {
                throw new IOException("파일 로딩에 오류가 발생했습니다", e);
            }
        }
        data.append("]");
        return data;
    }

    private List<Map<String, String>> strToMap(String strData) throws IOException {
        try {
            //정규 표현식으로 {} 안에 있는 내용을 리스트로 반환
            String regex = "\\{(.|\\n)*?\\}";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(strData);
            List<Map<String, String>> elements = new ArrayList<>();

            //각 {} 요소 파싱
            while (matcher.find()) {
                Map<String, String> item = new HashMap<>();
                String strItem = matcher.group();
                String[] fields = strItem.split(",");

                // 각 {} 요소의 키-값 파싱
                for (String field : fields) {
                    try {
                        String key = field.split(":")[0].
                            replace("\"", "").
                            replace("{", "").
                            replace("}", "").
                            trim();
                        String value = field.split(":")[1].
                            replace("\"", "").
                            replace("{", "").
                            replace("}", "").
                            trim();
                        item.put(key,value);
                    } catch (IndexOutOfBoundsException _) {
                    }
                }
                elements.add(item);
            }

            return elements;
        } catch (Exception e) {
            throw new IOException("올바르지 않은 파일 형식", e);
        }

    }

    private List<Map<String, String>> load() {
        try {
            String strData = loadStringFile().toString();
            return strToMap(strData);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private String mapToStr(List<Map<String, String>> maps) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Map<String, String> map : maps) {
            sb.append("\t{\n");
            sb.append("\t\t\"id\" : \"");
            sb.append(map.get("id"));
            sb.append("\"");
            sb.append(",\n");

            sb.append("\t\t\"content\" : \"");
            sb.append(map.get("content"));
            sb.append("\"");
            sb.append(",\n");

            sb.append("\t\t\"author\" : \"");
            sb.append(map.get("author"));
            sb.append("\"\n");

            sb.append("\t},\n");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    public void build() {
        String strData = mapToStr(data);

        try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/data.json");) {
            writer.write(strData);
        } catch (IOException e) {
            throw new RuntimeException("빌드에 실패했습니다", e);
        }
    }

    public void save(Map<String, String> data) {
        List<Map<String, String>> mapData = new ArrayList<>();
        mapData.add(data);

        //id.json 형식으로 저장
        String strData = mapToStr(mapData).replace("[", "").replace("]", "");
        try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/\{data.get("id")}.json");) {
            writer.write(strData);
        } catch (IOException e) {
            throw new RuntimeException("저장에 실패했습니다", e);
        }
        //lastId.txt 저장
        try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/lastId.txt");) {
            writer.write(getLastID());
        } catch (IOException e) {
            throw new RuntimeException("저장에 실패했습니다", e);
        }

    }
    private String getLastID() {
        try {
            int max = 0;
            for (Map<String, String> d : data) {
                if (Integer.parseInt(d.get("id")) > max) {
                    max = Integer.parseInt(d.get("id"));
                }
            }
            return Integer.toString(max);
        } catch (NoSuchElementException e) {
            return "0";
        }
    }

    public void create() {
        Console.print("명언 : ");
        String wise = Console.getInput();
        Console.print("작가 : ");
        String author = Console.getInput();

        int intItemID = Integer.parseInt(getLastID()) + 1;
        String itemID = Integer.toString(intItemID);

        Map<String, String> item = new HashMap<>();
        item.put("id", itemID);
        item.put("content", wise);
        item.put("author", author);
        data.add(item);
        save(item);
        Console.print(itemID + "번 명언이 등록되었습니다.\n");

    }

    public void listUp() {
        Console.print("번호 / 작가 / 명언\n" +
            "----------------------\n");
        StringBuilder sb = new StringBuilder();
        for (Map<String, String> d : data) {
            sb.append(d.get("id"));
            sb.append(" / ");
            sb.append(d.get("content"));
            sb.append(" / ");
            sb.append(d.get("author"));
            sb.append("\n");
        }
        Console.print(sb.toString());
    }

    private void modifyLastId(String id) {
        try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/lastId.txt");) {
            writer.write(id);
        } catch (IOException e) {
            throw new RuntimeException("수정에 실패했습니다", e);
        }
    }
    public void delete(String id) {
        Map<String, String> target = new HashMap<>();
        for (Map<String, String> d : data) {
            if (Objects.equals(d.get("id"), id)) {
                target = d;
                break;
            }
        }
        data.remove(target);

        File file = new File(STR."\{FILE_DIR}/\{id}.json");
        if (file.exists()) { // 파일이 존재하는 경우
            if (file.delete()) {
                Console.print(STR."\{id}번 명언이 삭제되었습니다.\n");
            } else {
                Console.print("파일 삭제에 실패했습니다\n");
            }
        } else {
            Console.print(STR."\{id}번 명언은 존재하지 않습니다.\n");
        }

        //최댓값이 달라졌을 때 수정
        if (Integer.parseInt(getLastID()) < Integer.parseInt(id)) {
            modifyLastId(id);
        }
    }

    public void update(String id) {

        //탐색
        Map<String, String> target = new HashMap<>();
        int index = 0;
        for (Map<String, String> d : data) {
            if (Objects.equals(d.get("id"), id)) {
                target = d;
                break;
            }
            index += 1;
        }

        if (target.isEmpty()) {
            Console.print(STR."\{id}번 명언은 존재하지 않습니다.\n");
            return;
        }

        Console.print(STR."명언(기존) : \{target.get("content")}\n");
        Console.print("명언 : ");
        String newContent = Console.getInput();

        Console.print(STR."\n작가(기존) : \{target.get("author")}\n");
        Console.print("작가 : ");
        String newAuthor = Console.getInput();

        Map<String, String> newData = new HashMap<>();
        newData.put("id", id);
        newData.put("content", newContent);
        newData.put("author", newAuthor);

        data.set(index, newData);

        //파일 덮어씌우기
        save(newData);

    }
}

class App {
    Controller controller = new Controller();

    public void execute() {
        Console.printWelcome();
        while (true) {
            Command command = Console.getCommand();

            if (command == Command.EXIT) break;
            if (command == Command.BUILD) controller.build();
            if (command == Command.CREATE) controller.create();
            if (command == Command.LIST_ALL) controller.listUp();
            if (command == Command.DELETE) {
                String args = Console.getArgs();
                try {
                    String id = args.split("=")[0];
                    String value = args.split("=")[1];
                    controller.delete(value);
                } catch (IndexOutOfBoundsException e) {
                    Console.print("입력 값이 올바르지 않습니다");
                }
            }
            if (command == Command.UPDATE) {
                String args = Console.getArgs();
                try {
                    String id = args.split("=")[0];
                    String value = args.split("=")[1];
                    controller.update(value);
                } catch (IndexOutOfBoundsException e) {
                    Console.print("입력 값이 올바르지 않습니다\n");
                }
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        App app = new App();
    }
}




