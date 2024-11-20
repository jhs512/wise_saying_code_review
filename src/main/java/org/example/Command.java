package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {
    APPLY("등록"),
    LIST("목록"),
    DELETE("삭제"),
    EDIT("수정"),
    BUILD("빌드"),
    QUIT("종료"),
    UNKNOWN("알 수 없음");

    public final String name;
    private static final String editRegex = "^수정\\?id=(\\d+)$";
    private static final String deleteRegex = "^삭제\\?id=(\\d+)$";
    private static final String listRegex = "^목록\\?keywordType=(author|content)&keyword=(.+)$";

    Command(String name) {
        this.name = name;
    }

    public static Command findByCommand(String command) {
        for (Command c : Command.values()) {
            if (match(editRegex, command)) {
                return EDIT;
            } else if (match(deleteRegex, command)) {
                return DELETE;
            } else if (match(listRegex, command)) {
                return LIST;
            } else if (c.name.equals(command)) {
                return c;
            }
        }
        return UNKNOWN;
    }

    public int getId(String command) throws ArrayIndexOutOfBoundsException {
        return Integer.parseInt(command.split("\\?id=")[1]);
    }

    public String getKeywordType(String command) {
        Pattern pattern = Pattern.compile(listRegex);
        Matcher matcher = pattern.matcher(command);

        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public String getKeyword(String command) {
        Pattern pattern = Pattern.compile(listRegex);
        Matcher matcher = pattern.matcher(command);

        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            return null;
        }
    }

    private static boolean match(String regex, String name) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }
}
