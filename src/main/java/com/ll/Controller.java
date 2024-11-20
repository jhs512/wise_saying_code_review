package com.ll;

public class Controller {
  Service controller = new Service();

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
      if (command == Command.SEARCH) {
        String args = Console.getArgs();
        try {
          String[] parsedArgs = args.split("&");
          String key1 = parsedArgs[0].split("=")[0];
          String keyword = parsedArgs[0].split("=")[1];

          String key2 = parsedArgs[1].split("=")[0];
          String keywordType = parsedArgs[1].split("=")[1];
          controller.search(keyword, keywordType);

        } catch (IndexOutOfBoundsException e) {
          Console.print("입력 값이 올바르지 않습니다\n");
        }
      }
    }
  }
}
