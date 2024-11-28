package wisesaying_tdd.wisesaying.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wisesaying_tdd.WiseSaying;
import wisesaying_tdd.wisesaying.Util.FileUtil;
import wisesaying_tdd.wisesaying.Util.JsonEditor;

public class WiseSayingRepository {
    public static File f;
    public static BufferedWriter bw;
    public static BufferedReader br;
    public static List<WiseSaying> repoList;
    private static String msg;

    // public int GetId(String cmd) {
    //     int id = 0;
    //     String filePath = FileUtil.GetFilePath(cmd);
    //     f = new File(filePath + "lastId.txt");

    //     try {
    //         br = new BufferedReader(new FileReader(f));
    //         id = Integer.parseInt(br.readLine());
    //         br.close();
    //     } catch (Exception e) {

    //     }

    //     return id;
    // }
    
    
        
    // 명언 파일 추가 
    public String AddWiseSaying(String cmd,int id, String wiseSaying, String authur) {
        msg = "";
        //String filePath = WiseSayingRepository.GetFilePath(cmd);
        String filePath = FileUtil.GetFilePath(cmd);

        try {
            f = new File(filePath+"lastId.txt");
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(Integer.toString(id));
            bw.close();

            String json = JsonEditor.MakeJson(Integer.toString(id),wiseSaying,authur);
            
            f = new File(filePath + id + ".json");
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(json);
            bw.close();

            msg = "addSuccess";


        } catch (Exception e) {
            msg = "addFailed";
        }
        

        return msg;

    }

    public List<WiseSaying> ShowWiseSaying(String cmd) {
        Map<String,String> wiseSayingMap = new HashMap<>();

        repoList = new ArrayList<>();
        String filePath = FileUtil.GetFilePath(cmd);
        f = new File(filePath+"lastId.txt");

        try {
            br = new BufferedReader(new FileReader(f));        
            int lastId = Integer.parseInt(br.readLine());
            
            for(int i = lastId; i > 0; i--) {
                try {
                    f = new File(filePath + i +".json");
                    wiseSayingMap = JsonEditor.jsonToMap(f);
                    repoList.add(new WiseSaying(Integer.parseInt(wiseSayingMap.get("id")), wiseSayingMap.get("content"), wiseSayingMap.get("authur")));
                } catch (Exception e) {
                    
                }
            }
            br.close();
            
        } catch (Exception e) {
            
        }

        return repoList;
    }

    public String DeleteWiseSaying(String cmd,int id) {
        String filePath = FileUtil.GetFilePath(cmd);
        f = new File(filePath+id+".json");

        if(f.exists()) {
            if(f.delete()) {
                msg = "deleteSuccess";
            } else {
                msg = "deleteFail";
            }
        } else {
            msg = "fileNotExists";
        }
        

        return msg;
    }

    public String BuildWiseSaying(String cmd) {
        StringBuilder sb = new StringBuilder();
        String filePath = FileUtil.GetFilePath(cmd);
        int lastId = FileUtil.GetId(cmd);

        sb.append("[");
        sb.append("\n");
        for(int i = 1 ; i <= lastId; i++) {
            f = new File(filePath + i + ".json");
            try {
                Map<String,String> buildMap = JsonEditor.jsonToMap(f);
                
                f = new File(filePath + "build.json");
                String jsonTxt = JsonEditor.MakeBuild(buildMap.get("id"), buildMap.get("content"), buildMap.get("authur"));
                sb.append(jsonTxt);
                if(i != lastId) {
                    sb.append(",");
                }
                sb.append("\n");
                
                
            } catch (Exception e) {
                
            }
        }
        sb.append("]");

        try {
            f = new File(filePath + "data.json");
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(sb.toString());
            bw.close();
            
            msg = "buildSuccess";
        } catch (Exception e) {
            msg = "buildFail";
        }
        
        return msg;
    }
    
}
