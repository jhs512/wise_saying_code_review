package org.example.common;

public enum Command {
    END("종료"),
    BUILD("빌드"),
    REGISTER("등록"),
    LIST("목록"),
    MODIFY("수정"),
    DELETE("삭제");

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Command fromDescription(String description) {
        for (Command command : Command.values()) {
            if (command.getDescription().equals(description)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown Command: " + description);
    }
}
