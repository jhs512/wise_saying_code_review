package com.ll;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

public class Controller {
  Service controller;

  Controller(Boolean isTestMode) {
    controller = new Service(isTestMode);
  }

  Controller() {
    this(false);
  }

  public Map<String, String> argParse() {
    String args = Console.getArgs();
    String[] parsedArgs = args.split("&");
    Map<String, String> item = new HashMap<>();

    try {
      for (String parsedArg : parsedArgs) {
        String key = parsedArg.split("=")[0];
        String value = parsedArg.split("=")[1];
        item.put(key, value);
      }
    } catch (IndexOutOfBoundsException e) {
      Console.print("입력 값이 올바르지 않습니다\n");
    }
    return item;
  }

  public void create() {
    Console.print("명언 : ");
    String wise = Console.getInput();
    Console.print("작가 : ");
    String author = Console.getInput();
    Result result = controller.create(wise, author);
    if (result.isSuccess()) Console.print(STR."\{result.args()}번 명언이 등록되었습니다.");
  }

  public void build() {
    controller.build();
  }

  public void listUp(List<Map<String, String>> data) {
    Map<String, String> parsed = argParse();
    List<String> args = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "page")
    ).toList();
    if (args.isEmpty()) {
      parsed.put("page", "1");
      return;
    }
    String page = parsed.get("page");
    Console.print("번호 / 작가 / 명언\n" +
        "----------------------\n");
    Console.print(controller.listUp(data, page).args().toString());
  }

  public void listUp() {
    Map<String, String> parsed = argParse();
    List<String> args = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "page")
    ).toList();
    if (args.isEmpty()) {
      parsed.put("page", "1");
      return;
    }
    String page = parsed.get("page");

    Console.print("번호 / 작가 / 명언\n" +
        "----------------------\n");
    Console.print(controller.listUp(page).args().toString());
  }

  public void delete() {
    Map<String, String> parsed = argParse();
    List<String> args = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "id")
    ).toList();
    if (args.isEmpty()) {
      Console.print("id값이 없습니다");
      return;
    }
    Result result = controller.delete(parsed.get("id"));
    Console.print(result.args().toString());
  }

  public void update() {
    Map<String, String> parsed = argParse();
    List<String> args = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "id")
    ).toList();
    if (args.isEmpty()) {
      Console.print("id값이 없습니다");
      return;
    }

    String id = parsed.get("id");
    Result result = controller.search(id,"id");
    List<Map<String, String>> target = (List<Map<String, String>>) result.args();

    if (!result.isSuccess()) {
      Console.print(STR."\{id}번 명언 삭제에 실패했습니다.\n");
    }
    if (target.isEmpty()) {
      Console.print(STR."\{id}번 명언은 존재하지 않습니다.\n");
    }

    Console.print(STR."명언(기존) : \{target.getFirst().get("content")}\n");
    Console.print("명언 : ");
    String newContent = Console.getInput();

    Console.print(STR."\n작가(기존) : \{target.getFirst().get("author")}\n");
    Console.print("작가 : ");
    String newAuthor = Console.getInput();
    Result resultUpdate = controller.update(id, newContent, newAuthor);
    Console.print(resultUpdate.args().toString());
  }

  public void search() {
    Map<String, String> parsed = argParse();
    List<String> keywords = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "keyword")
    ).toList();
    List<String> keywordTypes = parsed.keySet().stream().filter(
        it-> Objects.equals(it, "keywordType")
    ).toList();

    if (keywords.isEmpty() || keywordTypes.isEmpty()) {
      Console.print("검색 쿼리가 잘못되었습니다");
      return;
    }

    String keyword = parsed.get("keyword");
    String keywordType = parsed.get("keywordType");

    Result result = controller.search(keyword, keywordType);

    if (!result.isSuccess()) {
      Console.print("검색에 실패했습니다. 인자를 확인해주세요");
      return;
    }

    List<Map<String, String>> data = (List<Map<String, String>>) result.args();
    listUp(data);
  }

  public void execute() {
    Console.printWelcome();
    while (true) {
      Command command = Console.getCommand();
      if (command == Command.EXIT) break;
      if (command == Command.BUILD) build();
      if (command == Command.CREATE) create();
      if (command == Command.LIST_ALL) listUp();
      if (command == Command.DELETE) delete();
      if (command == Command.UPDATE) update();
      if (command == Command.SEARCH) search();
      }
    }
  }
