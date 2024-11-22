package org.example.controller;

import org.example.controller.model.FamousSayingModel;
import org.example.service.FamousService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * 명언 관련 컨트롤러
 *
 * @author shjung
 * @since 2024. 11. 19.
 */
public class FamousController {

    private FamousService famousService;

    private static List<FamousSayingModel> famousSayingModels = new ArrayList<>();

    private Scanner scanner;

    private int id;

    public FamousController() {
        this.famousService = new FamousService();
        this.scanner = new Scanner(System.in);
    }

    public FamousController(Scanner scanner){
        this.famousService = new FamousService();
        this.scanner = scanner;
    }

    public void run(){
        // 마지막 ID에서 + 1를 더함
        // - 마지막 보다 하나 높은 ID를 등록하기 위함
        int lastId = this.readLastId() + 1;
        famousSayingModels = famousService.readFamousSayings("목록", "", "");

        System.out.println("== 명언 앱 ==");

        // 명령어 받을 변수 선언
        String value = "";

        // 종료 명령어가 나올 때까지 하는 반복문
        while(!value.equals("종료")){
            // 명령어 입력받는 부분
            System.out.print("명령) ");
            value = scanner.nextLine();

            if(value.equals("등록")){
                // 명언 등록 함수 호출
                lastId = this.addFamousSaying(lastId);
            }
            else if(value.contains("목록")){
                // 명언 목록 출력 함수 호출
                this.printFamousSayings(value);
            }
            else if(value.contains(("삭제?id="))){
                // 명언 삭제 함수 호출
                this.deleteFamousSaying(value);
            }
            else if(value.contains("수정?id=")){
                // 명언 수정 함수 호출
                this.updateFamousSaying(value);
            }
            else if(value.equals("빌드")){
                // data.json 파일 저장 함수 호출
                this.buildFamousSaying();
            }
        }
        scanner.close();
    }


    /**
     *
     * 명언 등록 함수
     *
     * @param id 마지막 ID
     *
     * @return 등록된 ID보다 한 단계 높은 숫자
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public int addFamousSaying(int id){
        // 명언 데이터 입력
        System.out.print("명언: ");
        String contents = scanner.nextLine();
        System.out.print("작가: ");
        String author = scanner.nextLine();
        String message = famousService.addFamousSaying(id, contents, author);
        System.out.println(message);
        return id + 1;
    }

    /**
     *
     * 명언 목록 출력 함수
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void printFamousSayings(String value){
        // 검색어
        String keyword = "";
        // 검색어 타입
        String keywordType = "";
        // 페이지 목록
        int pageNum = 1;

        if(!value.equals("목록")){
            // 물음표 기준으로 검색 조건 파싱
            String[] keywords = value.split("\\?")[1].split("&");
            for (String s : keywords) {
                if (s.contains("page")) {
                    pageNum = Integer.parseInt(s.replaceAll("[^0-9]", ""));
                } else if (s.contains("keywordType")) {
                    keywordType = s.split("=")[1];
                } else if (s.contains("keyword")) {
                    keyword = s.split("=")[1];
                }
            }
        }

        famousSayingModels = famousService.readFamousSayings(value, keyword, keywordType);

        int pages = (famousSayingModels.size() / 5) + (famousSayingModels.size() % 5 == 0 ? 0 : 1);

        if(pageNum > pages){
            System.out.println("페이지 범위를 넘어간 페이지입니다.");
            return;
        }

        famousSayingModels = famousSayingModels.stream().skip((pageNum - 1) * 5).limit(5).toList();
        System.out.println("----------------");
        System.out.println("검색 타입: " + keywordType);
        System.out.println("검색어: " + keyword);
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");
        famousSayingModels.stream().forEach(f -> {
            System.out.println(f.getId() + " / " + f.getAuthor() + " / " + f.getContents());
        });
        System.out.println("----------------");
        System.out.print("페이지: ");
        for(int i = 1; i < pages; i++){
            if(pageNum == i)System.out.print("[");
            System.out.print(" " + i + " ");
            if(pageNum == i)System.out.print("]");
            System.out.print(" / ");
        }
        if(pageNum == pages)System.out.print("[");
        System.out.print(" " + pages + " ");
        if(pageNum == pages)System.out.print("]");
        System.out.println();
    }

    /**
     *
     * 명언 수정 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void updateFamousSaying(String value){
        // 명령어에서 찾는 ID 추출
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));

        // 찾은 ID를 통해서 수정하고자 하는 명언 검색
        FamousSayingModel famousSayingModel = famousService.readFamousSaying(id);

        if(famousSayingModel == null){
            System.out.println(id + "번 명언이 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존): " + famousSayingModel.getContents());
        System.out.print("명언: ");
        String contents = scanner.nextLine();
        System.out.println("작가(기존): " + famousSayingModel.getContents());
        System.out.print("작가: ");
        String author = scanner.nextLine();

        famousSayingModel.setContents(contents);
        famousSayingModel.setAuthor(author);

        String message = famousService.updateFamousSaying(famousSayingModel);

        System.out.println(message);
    }

    /**
     *
     * 명언 삭제 함수
     *
     * @param value 명령어
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void deleteFamousSaying(String value){
        int id = Integer.parseInt(value.replaceAll("[^0-9]", ""));

        String message = famousService.deleteFamousSaying(id);
        System.out.println(message);
    }

    /**
     *
     * 마지막 ID 검색
     *
     * @return 마지막 ID
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public int readLastId(){
        return famousService.readLastId();
    }

    /**
     *
     * data.json 빌드
     *
     * @author shjung
     * @since 2024. 11. 20.
     */
    public void buildFamousSaying(){
        String message = famousService.createDataJson(famousSayingModels);
        System.out.println(message);
    }
}
