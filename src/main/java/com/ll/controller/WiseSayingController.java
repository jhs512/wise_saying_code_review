package com.ll.controller;

import com.ll.domain.WiseSaying;
import com.ll.service.WiseSayingService;
import com.ll.service.cond.SearchCond;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static com.ll.util.ParamUtil.parse;
import static java.lang.Integer.parseInt;

public class WiseSayingController {
    private static final int PAGE_SIZE = 5;

    private final Scanner sc;
    private final WiseSayingService service;

    public WiseSayingController(WiseSayingService service) {
        sc = new Scanner(System.in);
        this.service = service;
    }

    public WiseSayingController(Scanner sc, WiseSayingService service) {
        this.sc = sc;
        this.service = service;
    }

    private void printList(List<WiseSaying> wiseSayingList, int page) {
        System.out.println("번호 / 작가 / 명언");

        System.out.println("------------------");
        List<WiseSaying> pagingList = wiseSayingList.stream()
                .sorted((ws1, ws2) -> Math.toIntExact(ws2.getId() - ws1.getId()))
                .skip((long) (page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
        pagingList.forEach(ws -> System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent()));
        System.out.println("------------------");

        System.out.print("페이지 : ");
        int pageCount = wiseSayingList.size() / PAGE_SIZE;
        if(wiseSayingList.size() % PAGE_SIZE != 0) pageCount += 1;

        for(int i = 1; i <= pageCount; i++){
            if(i != page){
                System.out.print(" " + i);
            }else{
                System.out.print(" [" + i + "]");
            }
        }
        System.out.println();
    }

    public void printBySearch(String command){

        if(command.equals("목록")){
            // 아무 파라미터도 없는 경우, 모든 목록을 출력
            printList(service.findAllWiseSaying(), 1);
            return;
        }

        Map<String, String> params = parse(command);
        String keyword = params.getOrDefault("keyword", "");
        String keywordType = params.getOrDefault("keywordType", "");

        // 검색 조건으로 확인 및 출력
        printList(service.searchByCond(new SearchCond(keywordType, keyword)), parseInt(params.getOrDefault("page", "1")));
    }

    public long InsertWiseSaying(){
        WiseSaying ws = new WiseSaying();

        System.out.print("명언 : ");
        ws.setContent(sc.nextLine());
        System.out.print("작가 : ");
        ws.setAuthor(sc.nextLine());

        long wiseSayingId = service.addWiseSaying(ws);
        System.out.println(wiseSayingId + "번 명언이 등록되었습니다.");

        return wiseSayingId;
    }

    public void deleteWiseSaying(String command){

        Map<String, String> params = parse(command);
        long deleteId = Long.parseLong(params.getOrDefault("id", "0"));
        if(deleteId == 0){
            return;
        }

        if(service.removeWiseSaying(deleteId)){
            System.out.println(deleteId + "번 명언이 삭제되었습니다.");
        }else{
            System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
        }
    }

    public long updateWiseSaying(String command){

        Map<String, String> params = parse(command);
        long updateId = Long.parseLong(params.getOrDefault("id", "0"));
        if(updateId == 0){
            return updateId;
        }

        Optional<WiseSaying> wsOptional = service.searchById(updateId);
        if(wsOptional.isEmpty()){
            System.out.println(updateId + "번 명언은 존재하지 않습니다.");
            return 0;
        }

        WiseSaying updateWs = wsOptional.get();
        System.out.println("명언(기존) : " + updateWs.getContent());
        System.out.print("명언 : ");
        updateWs.setContent(sc.nextLine());

        System.out.println("작가(기존) : " + updateWs.getAuthor());
        System.out.print("작가 : ");
        updateWs.setAuthor(sc.nextLine());

        service.updateWiseSaying(updateWs);

        return updateId;
    }

    public void build() {
        if(service.buildWiseSaying()){
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        }else{
            System.out.println("data.json 파일의 갱신에 실패하였습니다.");
        }
    }
}
