package wisesaying_tdd.wisesaying.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

public class FileUtil {
    public static BufferedWriter bw;
    public static BufferedReader br;
    
    public static int GetId(String cmd) {
        int id = 0;
        String filePath = FileUtil.GetFilePath(cmd);
        File f = new File(filePath + "lastId.txt");

        try {
            br = new BufferedReader(new FileReader(f));
            id = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {

        }

        return id;
    }

    public static String GetFilePath(String mode) {
        String filePath = "";

        if (mode.trim().equals("test") || mode.trim().equals("테스트") || 
            mode.trim().equals("개발") || mode.trim().equals("TEST")) {
            filePath = "C:\\WorkSpace\\WiseSaying_2024_11_26\\WiseSaying_TDD\\app\\src\\test\\java\\wisesaying_tdd\\wisesaying\\db\\";
        } else {
            filePath = "C:\\WorkSpace\\WiseSaying_2024_11_26\\WiseSaying_TDD\\app\\src\\main\\java\\wisesaying_tdd\\wisesaying\\db\\";
        }

        return filePath;
    }
    
}
