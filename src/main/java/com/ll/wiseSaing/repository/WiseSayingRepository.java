package com.ll.wiseSaing.repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class WiseSayingRepository  {

    private static final String PATH = "D:\\son\\project\\programmers\\db\\wiseSaying\\";
    private Reader reader;
    private File file;
    private BufferedWriter bw;

    public JSONArray selectData() throws IOException, ParseException {
        file = new File(PATH+"data.json");
        Object obj = new Object();

        if (file.exists()) {
            reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            obj = parser.parse(reader);

            return (JSONArray) obj;
        }

        return null;
    }

    public int selectLastId() throws IOException, ParseException {
        file = new File(PATH + "lastId.txt");

        if (file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            return Integer.parseInt(br.readLine());
        }
        return 0;
    }

    public void insertData(JSONObject obj) throws IOException {

        file = new File(PATH + obj.get("id") + ".json");

        if (file.createNewFile()) {
            bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(obj.toJSONString());
            bw.flush();
            bw.close();
        }
    }

    public void insertLastId(int id) throws IOException {
        file = new File(PATH+"lastId.txt");
        if (!file.exists()) file.createNewFile();


        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
        bw.write(String.valueOf(id));
        bw.close();
    }

    public void deleteData(int id) {
        file = new File(PATH + id + ".json");


        file.delete();
    }

    public void updateData(JSONObject obj) throws IOException {
        file = new File(PATH + obj.get("id") + ".json");
        if (!file.exists()) file.createNewFile();

        bw = new BufferedWriter(new FileWriter(file, false));

        if (!obj.isEmpty()) {
            bw.write(obj.toJSONString());
            bw.flush();
            bw.close();
        }
    }
    public void build(JSONArray arr) throws IOException {
        file = new File(PATH+"data.json");
        if (!file.exists()) file.createNewFile();

        bw = new BufferedWriter(new FileWriter(file, false));
        bw.write(JSONArray.toJSONString(arr));
        bw.flush();
        bw.close();

        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
