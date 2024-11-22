package org.example.util;

public class QueryStringParser {

    public int getId(String queryString) {
        return Integer.parseInt(queryString.split("=")[1]);
    }

    public String[] getKeyword(String queryString) {
        // 목록?keywordType=content&keyword=과거
        // [목록, keywordType, content, keyword, 과거]
        String[] split = queryString.split("[?=&]");
        return new String[]{split[4], split[2]};    // 키워드, 타입
    }

    public int getPage(String queryString) {
        if (queryString.length() == 2) {
            return 1;
        } else if (!queryString.contains("&")) {
            // [명령?page, 1]
            String[] split = queryString.split("[=]");
//            System.out.println(Arrays.toString(split));
            return Integer.parseInt(split[split.length - 1]);
        } else {
            // [목록, keywordType, content, keyword, 과거, page, 1]
            String[] split = queryString.split("[?=&]");
//            System.err.println(Arrays.toString(split));
            if(split[split.length - 1].matches("^[0-9]$")) {
                return Integer.parseInt(split[split.length - 1]);
            }
            return 1;
        }

    }

}
