package baekgwa.global;

public enum Commands {

    REGISTER("등록"),
    EXIT("종료"),

    @Deprecated(since = "13단계", forRemoval = true)
    LIST("목록"), //목록 조회 기능 pagable 이 기본 설정으로 변경 예정.

    DELETE("삭제?id="),
    MODIFY("수정?id="),
    BUILD("빌드"),
    SEARCH("목록")
    ;

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}
