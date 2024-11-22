package org.example.repository;

import org.example.repository.entity.FamousSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * 명언 파일 관련 레포지토리
 *
 * @author shjung
 * @since 2024. 11. 19.
 */
public class FamousRepository {

    // 파일 저장 위치
    private static final String FILE_PATH = "/Users/shjung/Desktop/Java/db/wiseSaying";


    /**
     *
     * 명언과 마지막 ID를 같이 저장하는 함수
     *
     * @param famousSaying 명언 모델
     *
     * @return 성공 시 1, 실패 시 0 전달
     *
     * @author shjung
     * @since 2024. 11. 21.
     */
    public int createFile(FamousSaying famousSaying){
        int check = this.createFile(famousSaying, true);
        int lastIdCheck = this.createFile(famousSaying, false);

        if(check == 0 || lastIdCheck == 0){
            return 0;
        }
        return 1;
    }

    /**
     *
     * 파일 생성 함수
     *
     * @param famousSaying 명언 오브젝트
     * @param entity 명언 오브젝트를 저장할 것인지 아니면 마지막 ID만 저장할 것인지에 대한 변수
     *               - ture일 경우 명언 저장, false일 경우 ID만 저장
     *
     * @return 성공일 경우 1, 실패할 경우 0 리턴
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public int createFile(FamousSaying famousSaying, boolean entity){
        String filename = entity ? famousSaying.getId() + ".json" : "lastId.txt";
        File dataFile = new File(FILE_PATH + "/" + filename);

        if(entity){
            try{
                FileWriter fileWriter = new FileWriter(dataFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(famousSaying.toString());

                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e){
                e.printStackTrace();
                return 0;
            }
        }
        else {
            try{
                FileWriter fileWriter = new FileWriter(dataFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(String.valueOf(famousSaying.getId()));

                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e){
                e.printStackTrace();
                return 0;
            }
        }
        return 1;
    }

    /**
     *
     * data.json 생성 함수
     *
     * @param famousSayingList 명언 목록
     *
     * @return 성공시 1, 실패시 0 리턴
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public int createDataJson(List<FamousSaying> famousSayingList){
        File dataFile = new File(FILE_PATH + "/data.json");
        try{
            FileWriter fileWriter = new FileWriter(dataFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(famousSayingList.toString());

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     *
     * 마지막 ID 불러오는 함수
     *
     * @return 마지막 ID
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public int readLastId(){
        int number = 0;
        // 마지막 ID 저장된 파일 불러오기
        File lastIdFile = new File(FILE_PATH + "/lastId.txt");
        // 만약에 없을 경우 함수 종료
        if(!lastIdFile.isFile()){
            return number;
        }

        try {
            // 저장된 파일 읽어오기
            FileReader fileReader = new FileReader(lastIdFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 데이터 저장 변수
            String data = "";
            // 데이터 읽는 변수
            String line = "";
            // 모든 데이터 읽어서 데이터 저장 변수에 저장
            while((line = bufferedReader.readLine()) != null){
                data += line;
            }

            // 마지막 ID 에 저장
            number = Integer.parseInt(data);

            // 파일 읽기 종료
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return number;
    }

    /**
     *
     * 저장된 명언 데이터 읽어오는 함수
     *
     * @param filename 읽어올 파일 이름
     *
     * @return 읽어온 String 데이터
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public String readFamousSaying(String filename){
        if(!filename.endsWith(".json")) filename += ".json";
        File newFile = new File(FILE_PATH + "/" + filename);

        try{
            FileReader fileReader = new FileReader(newFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String data = "";
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                data += line;
            }
            return data;
        } catch (IOException e) {
            return "실패";
        }
    }

    /**
     *
     * 명언 파일 삭제 함수
     *
     * @param filename 삭제할 파일 이름
     * @return 성공 시 1, 실패 시 0 리턴
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public int deleteFamousSaying(String filename){
        File dataFile = new File(FILE_PATH + "/" + filename);

        try{
            // 파일이 존재할 경우 파일 삭제
            if(dataFile.isFile()){
                dataFile.delete();
            }
            else return 0;

        } catch (Exception e) {
            return 2;
        }
        return 1;
    }

    /**
     *
     * 디렉토리에 존재하는 모든 json 파일 이름 목록 조회
     *
     * @return json 파일 이름 목록
     *
     * @author shjung
     * @since 2024. 11. 19.
     */
    public List<String> findFilenames(){
        List<String> filenames = new ArrayList<>();

        File directory = new File(FILE_PATH);

        // 디렉토리가 존재하지 않을 경우 함수 종료
        if(!directory.exists()){
            return null;
        }

        try {
            // 파일 목록 필터링
            // - json 파일만 불러오기 위함
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith("json");
                }
            };
            // 파일 목록 저장
            File[] files = directory.listFiles(fileFilter);

            if(files != null) {
               // 파일 목록에서 이름만 저장
                filenames = Arrays.stream(files).map(f -> f.getName()).toList();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        // 목록에서 data.json 제외
        filenames = filenames.stream().filter(f -> !f.equals("data.json")).toList();

        // 명언 이름 목록을 배열로 변경
        String[] filenames1 = filenames.toArray(new String[filenames.size()]);

        // 배열에서 만들어진 순서대로 정렬
        Arrays.sort(filenames1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String s1 = o1.replaceAll("[^0-9]", "");
                String s2 = o2.replaceAll("[^0-9]", "");
                int a = Integer.parseInt(s1);
                int b = Integer.parseInt(s2);
                return a - b;
            }
        });

        filenames = Arrays.stream(filenames1).toList();

        return filenames;
    }
}
