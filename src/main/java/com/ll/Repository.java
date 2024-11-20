package com.ll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Repository {
  static String FILE_DIR = "src/main/resources/db/wiseSaying";
  public List<Map<String, String>> data;
  public Boolean isTestMode;

  Repository(Boolean isTestMode) {
    data = load();
    this.isTestMode = isTestMode;
  }

  Repository() {
    this(false);
  }

  private static File[] getFiles() {
    File directory = new File(FILE_DIR);

    if (directory.exists() && directory.isDirectory()) {
      return directory.listFiles();
    } else {
      return new File[0];
    }
  }

  private static StringBuilder loadStringFile() throws IOException {
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

  private static List<Map<String, String>> strToMap(String strData) throws IOException {
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

  public static List<Map<String, String>> load() {
    try {
      String strData = loadStringFile().toString();
      return strToMap(strData);
    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  private static String mapToStr(List<Map<String, String>> maps) {
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

  public static void build(List<Map<String, String>> data) {
    String strData = Repository.mapToStr(data);

    try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/data.json");) {
      writer.write(strData);
    } catch (IOException e) {
      throw new RuntimeException("빌드에 실패했습니다", e);
    }
  }

  public static void saveJson(Map<String, String> data) {
    List<Map<String, String>> mapData = new ArrayList<>();
    mapData.add(data);

    //id.json 형식으로 저장
    String strData = Repository.mapToStr(mapData).replace("[", "").replace("]", "");
    try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/\{data.get("id")}.json");) {
      writer.write(strData);
    } catch (IOException e) {
      throw new RuntimeException("저장에 실패했습니다", e);
    }
  }

  public static void saveLastId(String lastId) {
    //lastId.txt 저장
    try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/lastId.txt");) {
      writer.write(lastId);
    } catch (IOException e) {
      throw new RuntimeException("저장에 실패했습니다", e);
    }
  }

  public static void modifyLastId(String id) {
    try (FileWriter writer = new FileWriter(STR."\{FILE_DIR}/lastId.txt");) {
      writer.write(id);
    } catch (IOException e) {
      throw new RuntimeException("수정에 실패했습니다", e);
    }
  }
}


