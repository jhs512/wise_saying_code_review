package com.ll;

import java.io.File;
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

  public Result create(String wise, String author) {
    int intItemID = Integer.parseInt(getLastID()) + 1;
    String itemID = Integer.toString(intItemID);

    Map<String, String> item = new HashMap<>();
    item.put("id", itemID);
    item.put("content", wise);
    item.put("author", author);
    data.add(item);
    Repository.saveJson(item);
    Repository.saveLastId(getLastID());

    return new Result(true, itemID);
  }

  public Result listUp(List<Map<String, String>> data) {

    StringBuilder sb = new StringBuilder();
    for (Map<String, String> d : data) {
      sb.append(d.get("id"));
      sb.append(" / ");
      sb.append(d.get("content"));
      sb.append(" / ");
      sb.append(d.get("author"));
      sb.append("\n");
    }
    return new Result(true, sb);
  }

  public Result listUp() {
    return listUp(data);
  }

  public Result search(String keyword, String keywordType) {
    if (Objects.equals(keywordType, "content")) {
      List<Map<String, String>> result = data.stream().filter(
          it-> it.get("content").trim().contains(keyword.trim())
      ).toList();
      return new Result(true, result);
    }
    if (Objects.equals(keywordType, "author")) {
      List<Map<String, String>> result = data.stream().filter(
          it-> it.get("author").trim().contains(keyword.trim())
      ).toList();
      return new Result(true, result);
    }
    if (Objects.equals(keywordType, "id")) {
      List<Map<String, String>> result = data.stream().filter(
          it-> it.get("id").trim().contains(keyword.trim())
      ).toList();
      return new Result(true, result);
    }
    return new Result(false, "올바르지 않은 키워드 타입입니다");
  }

  public Result delete(String id) {
    Map<String, String> target = new HashMap<>();
    for (Map<String, String> d : data) {
      if (Objects.equals(d.get("id"), id)) {
        target = d;
        break;
      }
    }
    data.remove(target);

    //최댓값이 달라졌을 때 수정
    if (Integer.parseInt(getLastID()) < Integer.parseInt(id)) {
      Repository.modifyLastId(id);
    }

    File file = new File(STR."\{FILE_DIR}/\{id}.json");
    if (file.exists()) { // 파일이 존재하는 경우
      if (file.delete()) {
        return new Result(true, STR."\{id}번 명언이 삭제되었습니다.\n");
      } else {
        return new Result(false, "파일 삭제에 실패했습니다\n");
      }
    } else {
      return new Result(false, STR."\{id}번 명언은 존재하지 않습니다.\n");
    }
  }

  public Result update(String id, String newContent, String newAuthor) {
    Map<String, String> newData = new HashMap<>();
    newData.put("id", id);
    newData.put("content", newContent);
    newData.put("author", newAuthor);

    int index = 0;
    for (Map<String, String> d : data) {
      if (Objects.equals(d.get("id"), id)) {
        break;
      }
      index += 1;
    }
    data.set(index, newData);

    //파일 덮어씌우기
    Repository.saveJson(newData);
    Repository.saveLastId(getLastID());

    return new Result(true, "변경에 성공했습니다");
  }
}