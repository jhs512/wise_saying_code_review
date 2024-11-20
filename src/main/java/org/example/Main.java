package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Set;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("== 명언 앱 ==");

        int id = 1;

        TreeMap<Integer, WiseSaying> map = new TreeMap<>();

        while(true) {
            System.out.print("명령) ");
            String command = br.readLine();

            if(command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                System.out.print("명언 : ");
                String content = br.readLine();
                System.out.print("작가 : ");
                String author = br.readLine();

                // 명언 객체 생성
                WiseSaying tmp = new WiseSaying(id, content, author);

                // 맵에 명언 객체 추가
                map.put(id, tmp);
                id++;

                try{
                    // 생성한 WiseSaying 객체 json 파일로 저장
                    String filePath = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying\\" + tmp.id + ".json";
                    mapper.writeValue(new File(filePath), tmp);

                    // 가장 최근에 생성된 id 로 lastId.txt 내용 갱신
                    String filePath2 = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying\\lastId.txt";
                    FileWriter writer = new FileWriter(filePath2);
                    writer.write(String.valueOf(id-1));
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (command.startsWith("삭제")) {
                int remove_id = Integer.parseInt(command.substring(6));

                // 존재하는 id 라면 map, json 파일 모두 삭제
                if(map.containsKey(remove_id)) {
                    String filePath = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying\\" + remove_id + ".json";
                    File file = new File(filePath);
                    boolean isDeleteSuccess = file.delete();
                    map.remove(remove_id);
                }

                // 존재하지 않는 id 예외 처리
                else {
                    System.out.println(remove_id + "번 명언은 존재하지 않습니다.");
                }
            } else if (command.startsWith("수정")) {

                // 수정할 id
                int fix_id = Integer.parseInt(command.substring(6));

                // 수정할 명언 객체
                WiseSaying tmp = map.get(fix_id);

                if(tmp == null) {
                    System.out.println(fix_id + "번 명언은 존재하지 않습니다.");
                } else {
                    System.out.println("명언(기존): " + tmp.content);
                    System.out.print("명언 : ");
                    String temp_content = br.readLine();

                    System.out.println("작가(기존): " + tmp.author);
                    System.out.print("작가 : ");
                    String temp_author = br.readLine();

                    // 수정된 명언 객체 생성 후 map, json 파일 모두 반영
                    WiseSaying fixedWiseSaying = new WiseSaying(fix_id, temp_content, temp_author);
                    map.put(fix_id, new WiseSaying(fix_id, temp_content, temp_author));
                    try{
                        String filePath = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying\\" + tmp.id + ".json";
                        mapper.writeValue(new File(filePath), fixedWiseSaying);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (command.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("------------------");

                Set<Integer> ids = map.keySet();

                // map 객체의 모든 명언 객체 문자열로 출력
                for(int i : ids) {
                    WiseSaying tmp = map.get(i);
                    System.out.println(i + " / " + tmp.author + "/" + tmp.content);
                }
            } else if (command.equals("빌드")) {
                try {
                    // map의 모든 객체를 통째로 1개의 json 파일로 저장
                    String filePath = "C:\\Users\\kskw2\\OneDrive\\바탕 화면\\프로그래머스 KDT\\2024-11-18-wise-saying-app\\db\\wiseSaying\\data.json";
                    mapper.writeValue(new File(filePath), map);
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static class WiseSaying {
        public int id;
        public String content;
        public String author;

        WiseSaying(int id, String content, String author) {
            this.id = id;
            this.content = content;
            this.author = author;
        }
    }
}