package com.ll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Service {
  static String FILE_DIR = "src/main/resources/db/wiseSaying";
  public List<Map<String, String>> data;
  public Boolean isTestMode;

  Service(Boolean isTestMode) {
    data = Repository.load();
    this.isTestMode = isTestMode;
  }

  Service() {
    this(false);
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

  public void build() {
    Repository.build(data);
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
    Repository.saveJson(item);
    Repository.saveLastId(getLastID());
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
      Repository.modifyLastId(id);
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
    Repository.saveJson(newData);
    Repository.saveLastId(getLastID());
  }
}