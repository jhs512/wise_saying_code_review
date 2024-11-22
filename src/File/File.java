package File;

import WiseSayingRepository.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
        final static  String url = ".\\db\\wiseSaying\\data.json";
        static public void save(JsonArray jarr) {
            try {
                FileWriter file = new FileWriter(url);
                file.write(jarr.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        static public String load(){
            try {
                Path filePath = Paths.get(url);
                String content = Files.readString(filePath);
                return content;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        static public String load(String url_){
            try {
                Path filePath = Paths.get(url_);
                String content = Files.readString(filePath);
                return content;
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        static public void save(JsonArray jarr,String url_) {
            try {
                FileWriter file = new FileWriter(url_);
                file.write(jarr.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
