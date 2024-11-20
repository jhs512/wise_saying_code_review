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

  public void execute() {
    Console.printWelcome();
    while (true) {
      Command command = Console.getCommand();

      if (command == Command.EXIT) break;
      if (command == Command.BUILD) controller.build();
      if (command == Command.CREATE) controller.create();
      if (command == Command.LIST_ALL) controller.listUp();
      if (command == Command.DELETE) {
        List<Map<String, String>> parsed = argParse();
        List<Map<String, String>> args = parsed.stream().filter(
            it-> Objects.equals(it.get("key"), "id")
        ).toList();
        if (args.isEmpty()) {
          Console.print("id값이 없습니다");
          continue;
        }
        controller.delete(args.getLast().get("value"));
      }
      if (command == Command.UPDATE) {
        List<Map<String, String>> parsed = argParse();
        List<Map<String, String>> args = parsed.stream().filter(
            it-> Objects.equals(it.get("key"), "id")
        ).toList();
        if (args.isEmpty()) {
          Console.print("id값이 없습니다");
          continue;
        }
        controller.update(args.getLast().get("value"));
      }
      if (command == Command.SEARCH) {
        List<Map<String, String>> parsed = argParse();
        List<Map<String, String>> keywords = parsed.stream().filter(
            it-> Objects.equals(it.get("key"), "keyword")
        ).toList();
        List<Map<String, String>> keywordTypes = parsed.stream().filter(
            it-> Objects.equals(it.get("key"), "keywordType")
        ).toList();

        if (keywords.isEmpty() || keywordTypes.isEmpty()) {
          Console.print("검색 쿼리가 잘못되었습니다");
          continue;
        }
        String keyword = keywords.getLast().get("value");
        String keywordType = keywordTypes.getLast().get("value");
        controller.search(keyword, keywordType);
        }
      }
    }
  }
