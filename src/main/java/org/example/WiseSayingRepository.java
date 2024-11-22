package org.example;

import java.io.*;
import java.util.ArrayList;

public class WiseSayingRepository {
    ArrayList<WiseSaying> DB = new ArrayList<>();
    private String dbDir = ".\\src\\main\\db\\wiseSaying\\";

    //테스트용 DB디렉토리 대체용
    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void getAllDB() throws IOException {
        File lIdFile = new File(dbDir + "lastId.txt");
        File wsFile = null;

        BufferedReader br = new BufferedReader(new FileReader(lIdFile));
        int lId = Integer.parseInt(br.readLine());
        String line = "", saying = "", writter = "";

        for (int i = lId; i > 0; i--) {
            wsFile = new File(dbDir + i + ".json");
            if (wsFile.exists()) {
                br = new BufferedReader(new FileReader(wsFile));
                while ((line = br.readLine()) != null) {
                    if (line.contains("\"content\": ")) {
                        saying = line.split("\"")[3];
                    }
                    if (line.contains("\"author\": ")) {
                        writter = line.split("\"")[3];
                    }
                }
                DB.add(new WiseSaying(i, saying, writter));
            }
        }
        br.close();
    }

    public void setLastId(int id) {
        File lastId = new File(dbDir + "lastId.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(lastId));
            bw.write(String.valueOf(id));

            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getLastId() throws IOException {
        File lastId = new File(dbDir + "lastId.txt");
        int id = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(lastId));
            id = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        File nsw = new File(dbDir + wiseSaying.getId() + ".json");
        nsw.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(nsw));
        writeWiseSayingJSONFile(wiseSaying, bw);

        bw.flush();
        bw.close();

        DB.addFirst(wiseSaying);
    }

    public ArrayList<WiseSaying> findPagedWiseSayings(Pageable pageable) {
        return getPagedWiseSayings(DB, pageable);
    }

    public WiseSaying findById(int id) {
        WiseSaying result = null;
        for (WiseSaying ws : DB) {
            if (ws.getId() == id) {
                result = ws;
                break;
            }
        }
        return result;
    }

    public void removeById(int id) {
        File rmsw = new File(dbDir + id + ".json");
        if (!rmsw.exists()) throw new RuntimeException();

        for (int i = 0; i < DB.size(); i++) {
            if(DB.get(i).getId() == id) {
                DB.remove(i);
                break;
            }
        }
        System.gc();
        rmsw.delete();
    }

    public void updateWiseSaying(int id, String wiseSaying, String writter) throws IOException {
        WiseSaying updateWS = findById(id);
        updateWS.setSaying(wiseSaying);
        updateWS.setWritter(writter);

        File uSW = new File(dbDir + updateWS.getId() + ".json");
        BufferedWriter bw = new BufferedWriter(new FileWriter(uSW));
        writeWiseSayingJSONFile(updateWS, bw);

        bw.flush();
        bw.close();
    }

    public void buildData() throws IOException {
        File data = new File(dbDir + "data.json");
        BufferedWriter bw = new BufferedWriter(new FileWriter(data));
        WiseSaying wiseSaying = null;

        bw.write("[");
        bw.newLine();
        for (int i = 0; i < DB.size(); i++) {
            wiseSaying = DB.get(i);

            writeWiseSayingJSONFile(wiseSaying, bw);
            if (i != DB.size() - 1) {
                bw.write(",");
                bw.newLine();
            }


        }
        bw.newLine();
        bw.write("]");

        bw.flush();
        bw.close();
    }

    public ArrayList<WiseSaying> serarchWiseSayingsBySaying(String keyword, Pageable pageable) {
        ArrayList<WiseSaying> result = new ArrayList<>();
        for (WiseSaying ws : DB) {
            if (ws.getSaying().contains(keyword)) result.add(ws);
        }
        return getPagedWiseSayings(result, pageable);
    }

    public ArrayList<WiseSaying> serarchWiseSayingsByWritter(String keyword, Pageable pageable) {
        ArrayList<WiseSaying> result = new ArrayList<>();
        for (WiseSaying ws : DB) {
            if (ws.getWritter().contains(keyword)) result.add(ws);
        }
        return getPagedWiseSayings(result, pageable);
    }

    // 명언 파일 작성 양식 매서드
    // 중복되는 코드이기에 매서드로 만듦
    private void writeWiseSayingJSONFile(WiseSaying wiseSaying, BufferedWriter bw) throws IOException {
        bw.write("{\n\t\"id\": " + wiseSaying.getId() + ",\n" +
                "\t\"content\": \"" + wiseSaying.getSaying() + "\",\n" +
                "\t\"author\": \"" + wiseSaying.getWritter() + "\"\n}");
    }

    private ArrayList<WiseSaying> getPagedWiseSayings(ArrayList<WiseSaying> wiseSayings, Pageable pageable) {
        int start = pageable.getOffset();   // 페이지 첫번째 명언 index
        int end = Math.min(start + pageable.getPageSize(), wiseSayings.size()); // 페이지 마지막 명언 index (마지막 페이지일 시 마지막 인덱스까지만)
        int maxPageNumber = wiseSayings.size() > pageable.getPageSize() ? wiseSayings.size()/ pageable.getPageSize() : 1;   //마지막 페이지 계산
        pageable.setMaxPageNumber(maxPageNumber);   // 마지막 페이지 설정
        return  new ArrayList<>(wiseSayings.subList(start, end)); //리스트 잘라서 반환
    }
}
