package org.example.converter;

import org.example.model.WiseSayingItem;

import java.util.Map;

public class Converter {

    public String singleConvertToJson(WiseSayingItem item) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{\n");

        jsonBuilder.append("  \"").append("id").append("\"").append(": ")
                .append(item.getId()).append(",").append("\n");
        jsonBuilder.append("  \"").append("content").append("\"").append(": ")
                .append("\"").append(item.getWiseSaying()).append("\",").append("\n");
        jsonBuilder.append("  \"").append("author").append("\"").append(": ")
                .append("\"").append(item.getAuthor()).append("\"\n");

        jsonBuilder.append("}");

    return jsonBuilder.toString();
    }
}
