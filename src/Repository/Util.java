package Repository;

import java.io.FileWriter;
import java.io.IOException;

public class Util {
    public static class File{
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
}
