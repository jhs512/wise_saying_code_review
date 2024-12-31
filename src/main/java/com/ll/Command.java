package com.ll;

public class Command {
    String command;
    long id;

    public Command(String input) {
        if (input.contains("?")) {
            String[] commands = input.split("\\?", 2);
            this.command = commands[0];
            this.id = this.splitCommand(commands);
        } else {
            this.command = input;
            this.id = 0;
        }
    }

    public long splitCommand(String[] commands) {
        if (!isValid(commands)) {
            return 0;
        }

        String[] restCommands = commands[1].split("=", 2);
        return restCommands[1].isEmpty() ? 0 : Integer.parseInt(restCommands[1]);
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
