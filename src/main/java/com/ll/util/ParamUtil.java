package com.ll.util;

import java.util.HashMap;
import java.util.Map;

public class ParamUtil {
    public static Map<String, String> parse(String queryString){
        int boundaryIndex = queryString.indexOf("?");
        if(boundaryIndex == -1){
            return new HashMap<>();
        }

        String query = queryString.substring(boundaryIndex + 1);
        String[] params = query.split("&");

        Map<String, String> result = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            if(keyValue.length != 2) continue;

            result.put(keyValue[0], keyValue[1]);
        }

        return result;
    }
}
