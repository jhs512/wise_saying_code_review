package org.example.util;

import org.example.model.WiseSayingItem;

import java.util.List;

public class Converter {

    public String singleConvertToJson(WiseSayingItem item) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("{\n");

        jsonBuilder.append("\t\"").append("id").append("\"").append(": ")
                .append(item.getId()).append(",").append("\n");
        jsonBuilder.append("\t\"").append("content").append("\"").append(": ")
                .append("\"").append(item.getWiseSaying()).append("\",").append("\n");
        jsonBuilder.append("\t\"").append("author").append("\"").append(": ")
                .append("\"").append(item.getAuthor()).append("\"\n");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    public WiseSayingItem parseToJava(String jsonString) {

        String id = jsonString.split("\"id\":")[1].split(",")[0].trim();
        String wiseSaying = jsonString.split("\"content\":")[1].split(",")[0].replace("\"","").trim();
        String author = jsonString.split("\"author\":")[1].split("}")[0].replace("\"","").trim();

        return WiseSayingItem.of(Long.parseLong(id), author, wiseSaying);

    }

    public String multipleConvertToJson(List<WiseSayingItem> items) {
        StringBuilder jsonBuilder = new StringBuilder();

        jsonBuilder.append("[\n");

        for (int i = 0; i < items.size(); i++) {
            WiseSayingItem item = items.get(i);

            jsonBuilder.append("  {").append("\n");
            jsonBuilder.append("    \"id\": ").append(item.getId()).append(",").append("\n");
            jsonBuilder.append("    \"content\": \"").append(item.getWiseSaying()).append("\",").append("\n");
            jsonBuilder.append("    \"author\": \"").append(item.getAuthor()).append("\"\n");
            jsonBuilder.append("  }");

            if (i < items.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }

}
