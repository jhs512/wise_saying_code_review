package org.example.util;

public class QueryStringParser {

    public static int getId(String queryString) {
        return Integer.parseInt(queryString.split("=")[1]);
    }

    public static String[] getKeyword(String queryString) {
        // 목록?keywordType=content&keyword=과거
        // [목록, keywordType, content, keyword, 과거]
        String[] split = queryString.split("[?=&]");
        return new String[]{split[4], split[2]};    // 키워드, 타입
    }

}
