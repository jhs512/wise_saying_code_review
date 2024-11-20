package com.ll;

import java.util.*;

public class Controller {
  Service controller = new Service();

  public List<Map<String, String>> argParse() {
    String args = Console.getArgs();
    List<Map<String, String>> result = new ArrayList<>();
    String[] parsedArgs = args.split("&");

    try {
      for (String parsedArg : parsedArgs) {
        String key = parsedArg.split("=")[0];
        String value = parsedArg.split("=")[1];
        Map<String, String> item = new HashMap<>();
        item.put(key, value);
        result.add(item);
      }
    } catch (IndexOutOfBoundsException e) {
      Console.print("입력 값이 올바르지 않습니다\n");
    }
    return result;
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
    Console.print("번호 / 작가 / 명언\n" +
        "----------------------\n");
    Console.print(controller.listUp(data).args().toString());
  }

  public void listUp() {
    Console.print("번호 / 작가 / 명언\n" +
        "----------------------\n");
    Console.print(controller.listUp().args().toString());
  }

  public void delete() {
    List<Map<String, String>> parsed = argParse();
    List<Map<String, String>> args = parsed.stream().filter(
        it-> Objects.equals(it.get("key"), "id")
    ).toList();
    if (args.isEmpty()) {
      Console.print("id값이 없습니다");
      return;
    }
    Result result = controller.delete(args.getLast().get("value"));
    Console.print(result.args().toString());
  }

  public void update() {
    List<Map<String, String>> parsed = argParse();
    List<Map<String, String>> args = parsed.stream().filter(
        it-> Objects.equals(it.get("key"), "id")
    ).toList();
    if (args.isEmpty()) {
      Console.print("id값이 없습니다");
      return;
    }

    String id = args.getLast().get("value");
    Result result = controller.search(id,"id");
    List<Map<String, String>> target = (List<Map<String, String>>) result.args();

    if (!result.isSuccess()) {
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
    List<Map<String, String>> parsed = argParse();
    List<Map<String, String>> keywords = parsed.stream().filter(
        it-> Objects.equals(it.get("key"), "keyword")
    ).toList();
    List<Map<String, String>> keywordTypes = parsed.stream().filter(
        it-> Objects.equals(it.get("key"), "keywordType")
    ).toList();

    if (keywords.isEmpty() || keywordTypes.isEmpty()) {
      Console.print("검색 쿼리가 잘못되었습니다");
      return;
    }
    String keyword = keywords.getLast().get("value");
    String keywordType = keywordTypes.getLast().get("value");
    Result result = controller.search(keyword, keywordType);
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
