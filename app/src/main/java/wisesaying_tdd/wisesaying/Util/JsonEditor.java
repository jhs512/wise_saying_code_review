package wisesaying_tdd.wisesaying.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonEditor {
    
    public static String MakeJson(String id, String wiseSaying, String authur) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\n");
        sb.append("    ");
        sb.append("\"");
        sb.append("id");
        sb.append("\"");
        sb.append(" : ");
        sb.append(id);
        sb.append(",");
        sb.append("\n");
        sb.append("    ");
        sb.append("\"");
        sb.append("content");
        sb.append("\"");
        sb.append(" : ");
        sb.append("\"");
        sb.append(wiseSaying);
        sb.append("\"");
        sb.append(",");
        sb.append("\n");
        sb.append("    ");
        sb.append("\"");
        sb.append("authur");
        sb.append("\"");
        sb.append(" : ");
        sb.append("\"");
        sb.append(authur);
        sb.append("\"");
        sb.append("\n");
        sb.append("}");
        String jsonTxt = sb.toString();
        return jsonTxt;
    }

    public static Map<String,String> jsonToMap(File filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Map<String,String> jsonMap = new HashMap<>();
        
        int line = br.read();

        try {
            for(int j = line ; j > 0; j--) {
                String jsonContent = br.readLine();
                if(jsonContent != null) {
                    jsonContent = jsonContent.replace("{", "")
                                             .replace("}", "")
                                             .replace("\"", "");
                    String[] fields = jsonContent.split(",");
    
                    for (String s : fields) {
                        String[] keyVal = s.split(" : ");
    
                        if(keyVal.length > 1) {
                            String key = keyVal[0].trim();
                            String val = keyVal[1].trim();
                            jsonMap.put(key, val);
    
                        }
                    }
                }
            }            
        } catch (Exception e) {
            
        }
        br.close();


        return jsonMap;
    }

    public static String MakeBuild(String id, String wiseSaying, String authur) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ");
        sb.append("{");
        sb.append("    ");
        sb.append("\n");
        sb.append("    ");
        sb.append("    ");
        sb.append("\"");
        sb.append("id");
        sb.append("\"");
        sb.append(" : ");
        sb.append(id);
        sb.append(",");
        sb.append("\n");
        sb.append("    ");
        sb.append("    ");
        sb.append("\"");
        sb.append("content");
        sb.append("\"");
        sb.append(" : ");
        sb.append("\"");
        sb.append(wiseSaying);
        sb.append("\"");
        sb.append(",");
        sb.append("\n");
        sb.append("    ");
        sb.append("    ");
        sb.append("\"");
        sb.append("authur");
        sb.append("\"");
        sb.append(" : ");
        sb.append("\"");
        sb.append(authur);
        sb.append("\"");
        sb.append("\n");
        sb.append("    ");
        sb.append("}");
        String buildTxt = sb.toString();
        return buildTxt;
    }
}
