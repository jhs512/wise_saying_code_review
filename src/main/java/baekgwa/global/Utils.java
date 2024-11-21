package baekgwa.global;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    //EX) 목록?keyword=작자&keywordType=author
    //out = Map 형식, {key -> value} => {keyword -> 작자}, {keywordType -> author}
    public static Map<String, String> extractKeyword(String commandString) {
        Map<String, String> orders = new HashMap<>();
        try {
            String subString = commandString.substring(commandString.indexOf('?') + 1);

            return Arrays.stream(subString.split("&"))
                    .map(pair -> pair.split("="))
                    .filter(keyValue -> keyValue.length == 2)
                    .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
        } catch (Exception e) {
            return orders;
        }
    }

    public static Long extractId(String command, String extractStr) {
        return Long.parseLong(command.substring(extractStr.length()));
    }
}
