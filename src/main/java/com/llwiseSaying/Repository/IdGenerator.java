package com.llwiseSaying.Repository;



import java.io.*;
import com.llwiseSaying.Config.Config;



public class IdGenerator {

    private  Config config;
    public IdGenerator(Config config) {
        this.config=config;
    }

    public void makeFile(int id) {
        String filePath = config.getDBPath() + "/" + config.getLastIdFile();
        String content = String.valueOf(id);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content); // 데이터 쓰기

        } catch (IOException e) {
            System.out.println("id.txt파일 작성 예외 발생");
        }

    }

    public int loadFile() {


        File file = new File(config.getDBPath(), config.getLastIdFile());
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(config.getDBPath() +"/"+config.getLastIdFile()))) {
                String line;
                line = reader.readLine();
                //마지막 일련번호 다음 부터 등록해야함
                return Integer.parseInt(line)+1;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return 1;
    }
}
