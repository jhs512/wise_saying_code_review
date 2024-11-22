package org.example.service;

import org.example.controller.model.FamousSayingModel;
import org.example.repository.FamousRepository;
import org.example.repository.entity.FamousSaying;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * 명언 관련 비즈니스 클래스
 *
 * @author shjung
 * @since 2024. 11. 19.
 */
public class FamousService {

    private static final FamousRepository famousRepository = new FamousRepository();

    /**
     *
     * 명언 등록 비즈니스 함수
     *
     * @param id 등록할 ID
     * @param contents 등록할 명언
     * @param author 등록할 작가
     *
     * @return 등록여부 메시지
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public String addFamousSaying(int id, String contents, String author){
        String message = "";

        FamousSaying famousSaying = new FamousSaying(id, contents, author);

        int check = famousRepository.createFile(famousSaying);

        if(check == 0){
            message = id + "번 명언 등록에 실패하였습니다.";
        } else {
            message = id + "번 명언이 등록되었습니다.";
        }

        return message;
    }

    /**
     *
     * 명언 목록 검색 비즈니스 함수
     *
     * @return 명언 목록
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public List<FamousSayingModel> readFamousSayings(String value, String keyword, String keywordType){
        List<FamousSayingModel> famousSayingModels = new ArrayList<>();

        // 명언 이름 목록 조회
        List<String> filenames = famousRepository.findFilenames();

        for(String filename : filenames){
            String data = famousRepository.readFamousSaying(filename);

            // 파일 데이터 분류
            // - 전부 다 String 값으로 불러오기 때문에
            String[] datas = data.split(",");

            // 명언 데이터 저장할 오브젝트 생성
            FamousSayingModel famousSaying = new FamousSayingModel();

            // ID 저장
            int id = Integer.parseInt(datas[0].replaceAll("[^0-9]", ""));
            // 명언 저장
            String contents = datas[1].split("\"")[3];
            // 작가 저장
            String author = datas[2].split("\"")[3];

            // 명언 오브젝트에 데이터 저장
            famousSaying.setId(id);
            famousSaying.setContents(contents);
            famousSaying.setAuthor(author);

            // 명언 목록에 명언 오브젝트 추가
            famousSayingModels.addFirst(famousSaying);
        }

        // 검색 기능 추가 필터링
        if(!value.equals("목록")){
            // 작가 검색일 경우
            if(keywordType.equals("author")){
                famousSayingModels = famousSayingModels.stream().filter(f -> f.getAuthor().contains(keyword)).toList();
            }
            // 명언 검색일 경우
            else{
                famousSayingModels = famousSayingModels.stream().filter(f -> f.getContents().contains(keyword)).toList();
            }
        }

        return famousSayingModels;
    }

    /**
     *
     * 단일 명언 검색 비즈니스 모델
     * - 수정할 때 필요
     *
     * @param id 검색하고자 하는 명언 ID
     *
     * @return 검색한 명언
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public FamousSayingModel readFamousSaying(int id){
        String data = famousRepository.readFamousSaying(id + "");

        if("실패".equals(data)){
            return null;
        }

        // 파일 데이터 분류
        // - 전부 다 String 값으로 불러오기 때문에
        String[] datas = data.split(",");

        // 명언 데이터 저장할 오브젝트 생성
        FamousSayingModel famousSaying = new FamousSayingModel();

        // ID 저장
        id = Integer.parseInt(datas[0].replaceAll("[^0-9]", ""));
        // 명언 저장
        String contents = datas[1].split("\"")[3];
        // 작가 저장
        String author = datas[2].split("\"")[3];

        // 명언 오브젝트에 데이터 저장
        famousSaying.setId(id);
        famousSaying.setContents(contents);
        famousSaying.setAuthor(author);

        return famousSaying;
    }

    /**
     *
     * 명언 수정 비즈니스 함수
     *
     * @param famousSayingModel 수정하고자 하는 명언
     *
     * @return 수정 여부 메시지
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public String updateFamousSaying(FamousSayingModel famousSayingModel){
        String message = "";

        FamousSaying famousSaying = new FamousSaying(famousSayingModel.getId(), famousSayingModel.getContents(), famousSayingModel.getAuthor());

        int check = famousRepository.createFile(famousSaying, true);

        if(check == 0) message = famousSaying.getId() + "번 명언이 존재하지 않습니다..";
        else if(check == 1) message = famousSaying.getId() + "번 명언이 수정되었습니다.";

        return message;
    }

    /**
     *
     * 명언 삭제 비즈니스 함수
     *
     * @param id 삭제하고자 하는 명언 ID
     *
     * @return 삭제 여부 메시지
     *
     * @author shjung
     * @since 2024. 11. 20
     */
    public String deleteFamousSaying(int id){
        String message = "";

        String filename = id + ".json";

        int check = famousRepository.deleteFamousSaying(filename);

        if(check == 0) message = id + "번 명언이 존재하지 않습니다.";
        else if(check == 1) message = id + "번 명언을 삭제하였습니다..";
        else if(check == 2) message = id + "번 명언을 삭제하는데 오류가 발생했습니다.";

        return message;
    }

    /**
     *
     * 마지막 ID 조회 비즈니스 함수
     *
     * @return 마지막 ID
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public int readLastId(){
        return famousRepository.readLastId();
    }

    /**
     *
     * data.json 빌드 비즈니스 함수
     *
     * @param famousSayingModels 명언 목록
     *
     * @return 빌드 여부 메시지
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public String createDataJson(List<FamousSayingModel> famousSayingModels){
        String message = "";
        List<FamousSaying> famousSayings = new ArrayList<>();

        famousSayingModels.stream().forEach(f -> {
            FamousSaying famousSaying = new FamousSaying(f.getId(), f.getContents(), f.getAuthor());
            famousSayings.add(famousSaying);
        });

        int check = famousRepository.createDataJson(famousSayings);

        if(check == 0) message = "data.json 갱신에 실패하였습니다.";
        else if(check == 1) message = "data.json 파일의 내용이 갱신되었습니다.";

        return message;
    }
}
