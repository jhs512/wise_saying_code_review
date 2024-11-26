package org.example.domain;

public enum Commands {
    REGISTER("등록"),
    EXIT("종료"),
    LIST("목록"),
    SEARCH("검색"),
    DELETE("삭제"),
    MODIFY("수정"),
    BUILD("빌드");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
