package com.ll;

import com.ll.global.exception.IdNotFoundException;

public class Command {
    String command;
    long id;

    public Command(String input) {
        if (input.contains("?")) {
            String[] commands = input.split("\\?", 2);
            this.command = commands[0];
            this.id = this.splitCommand(commands);
        } else {
            if (input.equals("삭제") || input.equals("수정")) {
                throw new IdNotFoundException(input + "?id= 형식으로 id 값을 입력해주세요.");
            }
            this.command = input;
        }
    }

    public long splitCommand(String[] commands) {
        if (!isValid(commands)) {
            throw new IdNotFoundException(commands[0] + "?id= 형식으로 id 값을 입력해주세요.");
        }

        String[] restCommands;

        try {
            restCommands = commands[1].split("=", 2);
            return Integer.parseInt(restCommands[1]);
        } catch (NumberFormatException e) {
            throw new IdNotFoundException("id 값을 제대로 입력해주세요");
        }
    }

    public boolean isValid(String[] commands) {
        if (!commands[1].startsWith("id=")) {
            return false;
        }

        return true;
    }

    public long getId() {
        return this.id;
    }
}
