package com.ll;

import java.io.File;
import java.util.*;

public class Service {
  public List<Map<String, String>> data;
  public Boolean isTestMode;
  public Repository repository;

  Service(Boolean isTestMode) {
    repository = new Repository(isTestMode);
    data = repository.load();
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
    repository.build(data);
  }

  public Result create(String wise, String author) {
    int intItemID = Integer.parseInt(getLastID()) + 1;
    String itemID = Integer.toString(intItemID);

    Map<String, String> item = new HashMap<>();
    item.put("id", itemID);
    item.put("content", wise);
    item.put("author", author);
    data.add(item);
    repository.saveJson(item);
    repository.saveLastId(getLastID());

    return new Result(true, itemID);
  }

  public Result listUp(List<Map<String, String>> data, String page) {
    int dataSize = data.size();
    final int pageInt = Integer.parseInt(page) - 1;
    final int pageItemLimit = 5;
    final int startIndex = pageItemLimit*pageInt;
    int endIndex = pageItemLimit*pageInt+5;
    if (endIndex>dataSize) {
      endIndex = dataSize;
    }

    StringBuilder sb = new StringBuilder();

    if (startIndex > dataSize) return new Result(false, sb);
    Comparator<Map<String, String>> comparator = (map1, map2) -> {
      return map2.get("id").compareTo(map1.get("id"));
    };
    data.sort(comparator);
    List<Map<String, String>> subData = data.subList(startIndex, endIndex);

    for (Map<String, String> d : subData) {
      sb.append(d.get("id"));
      sb.append(" / ");
      sb.append(d.get("content"));
      sb.append(" / ");
      sb.append(d.get("author"));
      sb.append("\n");
    }
    return new Result(true, sb);
  }

  public Result listUp(String page) {
    return listUp(data, page);
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
      repository.modifyLastId(id);
      repository.saveLastId(getLastID());
    }

    return repository.delete(id);
  }

  public Result update(String id, String newContent, String newAuthor) {
    Map<String, String> newData = new HashMap<>();
    newData.put("id", id);
    newData.put("content", newContent);
    newData.put("author", newAuthor);

    int index = 0;
    for (Map<String, String> d : data) {
      if (Objects.equals(d.get("id"), id)) {
        data.set(index, newData);
        break;
      }
      index += 1;
    }

    if (index >= data.size()) return new Result(false, "변경에 실패했습니다");

    //파일 덮어씌우기
    repository.saveJson(newData);
    repository.saveLastId(getLastID());

    return new Result(true, "변경에 성공했습니다");
  }
}