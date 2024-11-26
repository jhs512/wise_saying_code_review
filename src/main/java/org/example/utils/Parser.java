package org.example.utils;

import org.example.constants.GlobalConstants;
import org.example.entity.WiseSaying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    // 명령어에서 페이지 추출
    public static int extractPageFromCommand(String command) {
        int page = 1;
        if (command.contains("page")) {
            page = Integer.parseInt(command.split("\\?")[1]
                                            .split("&")[0]
                                            .split("=")[1]);
        }
        return page;
    }

    // 명령어에서 id 추출
    public static int extractIdFromCommand(String command) {
        String idStr = command.substring(6);
        int id = Integer.parseInt(idStr);

        return id;
    }

    // 명령어에서 keyword, keyword_type 을 추출하여 Map에 담아 반환
    public static Map<String, String> extractKeywordsFromCommand(String command) {

        Map<String, String> requestParams = new HashMap<>();

        String[] query = command.split("\\?");
        String[] params = query[1].split("&");

        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");

            if (param[0].equals(GlobalConstants.KEYWORD)) {
                requestParams.put(param[0], param[1]);
            } else if (param[0].equals(GlobalConstants.KEYWORD_TYPE)) {
                requestParams.put(param[0], param[1]);
            }
            // 예외 처리 추가 필요
        }

        return requestParams;
    }
    // 명언 객체를 문자열로 파싱하기
    public static String convertWiseSayingToString(WiseSaying wiseSaying, boolean build_flag) {

        String prefix;
        if(build_flag) {
            prefix = "\t";
        } else {
            prefix = "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append("{").append("\n");
        sb.append(prefix).append("\t\"id\"").append(" : ").append(wiseSaying.getId()).append(",\n");
        sb.append(prefix).append("\t\"content\"").append(" : \"").append(wiseSaying.getContent()).append("\",\n");
        sb.append(prefix).append("\t\"author\"").append(" : \"").append(wiseSaying.getAuthor()).append("\"\n");
        sb.append(prefix).append("}");

        return sb.toString();
    }

    // 문자열을 명언 객체로 파싱하기
    public static WiseSaying convertStringToWiseSaying (String wiseSayingString) {
        wiseSayingString = wiseSayingString.replace("{", "")
                .replace("}", "")
                .replace("\n", "")
                .replace("\t", "")
                .replace("\"", "");

        String[] tokens = wiseSayingString.split(",");

        int id = Integer.parseInt(tokens[0].split("\\s*:\\s*")[1].trim());
        String content = tokens[1].split("\\s*:\\s*")[1].trim();
        String author = tokens[2].split("\\s*:\\s*")[1].trim();

        return new WiseSaying(id, content, author);
    }

    // 명언 객체 리스트를 data.json 문자열로 파싱
    public static String convertWiseSayingListToString (List<WiseSaying> wiseSayingList) {
        StringBuilder sb = new StringBuilder();
        int size = wiseSayingList.size();
        // 모든 wiseSaying json 문자열 합쳐서 출력하기
        sb.append("[").append("\n");
        for (int i = 0; i < size; i++) {
            WiseSaying wiseSaying = wiseSayingList.get(i);
            String tmp = Parser.convertWiseSayingToString(wiseSaying, true);
            if (i < size - 1) {
                sb.append(tmp).append(" ,").append("\n");
            } else {
                sb.append(tmp).append("\n");
            }
        }
        sb.append("]").append("\n");

        return sb.toString();
    }
}
