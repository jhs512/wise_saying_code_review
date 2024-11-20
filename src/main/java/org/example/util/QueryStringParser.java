package org.example.util;

public class QueryStringParser {

    public static int getId(String queryString) {
        return Integer.parseInt(queryString.split("=")[1]);
    }

}
