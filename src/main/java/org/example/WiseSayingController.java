package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WiseSayingController {
    private WiseSayingService wiseSayingService = new WiseSayingService();
    private BufferedReader br;

    public WiseSayingController(BufferedReader br) {
        this.br = br;
    }

    public void setWiseSayingService(WiseSayingService wiseSayingService) {
        this.wiseSayingService = wiseSayingService;
    }

    public void atStartRun() throws IOException {
        wiseSayingService.atStartRun();
    }

    public void regist() throws IOException {
        System.out.print("명언 : ");
        String wiseSaying = br.readLine();
        System.out.print("작가 : ");
        String writter = br.readLine();

        int id = wiseSayingService.registWiseSaying(wiseSaying, writter);
        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void viewWiseSayings(String cmd) {
        Map<String, String> param = extractParam(cmd);
        int page = 1;
        if(param.containsKey("page") && param.get("page") != null) page = Integer.parseInt(param.get("page"));
        Pageable pageable = new Pageable(page);

        viewWiseSayingList(wiseSayingService.viewPagedWiseSayings(pageable), pageable);
    }

    public void remove(String cmd) {
        int removeId = Integer.parseInt(extractParam(cmd).get("id"));
        System.out.println(extractParam(cmd));
        try {
            wiseSayingService.removeWiseSaying(removeId);
            System.out.println(removeId + "번 명언이 삭제되었습니다.");
        } catch (Exception e) {
            System.out.println(removeId + "번 명언은 존재하지 않습니다.");
        }
    }

    public void update(String cmd) throws IOException {
        int updateId = Integer.parseInt(extractParam(cmd).get("id"));
        WiseSaying ws = wiseSayingService.findWiseSayingById(updateId);

        System.out.println("명언(기존) : " + ws.getSaying());
        System.out.print("명언 : ");
        String updateSaying = br.readLine();

        System.out.println("작가(기존) : " + ws.getWritter());
        System.out.print("작가 : ");
        String updateWritter = br.readLine();

        wiseSayingService.updateWiseSaying(updateId, updateSaying, updateWritter);
    }

    public void buildData() throws IOException {
        wiseSayingService.buildData();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void searchResult(String cmd) throws IOException {
        Map<String, String> param = extractParam(cmd);
        int page = 1;
        if(param.containsKey("page") && param.get("page") != null) page = Integer.parseInt(param.get("page"));

        Pageable pageable = new Pageable(page);
        String type = param.get("type");
        String keyword = param.get("keyword");

        System.out.println("------------------------");
        System.out.println("검색타입 : " + type);
        System.out.println("검색어 : " + keyword);
        System.out.println("------------------------");

        viewWiseSayingList(wiseSayingService.searchWiseSaying(keyword, type, pageable), pageable);
    }

    private void viewWiseSayingList(ArrayList<WiseSaying> wiseSayingArrayList, Pageable pageable) {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("------------------------");

        for(WiseSaying ws : wiseSayingArrayList) {
            System.out.println(ws.getId() + " / " + ws.getWritter() + " / " + ws.getSaying());
        }
        System.out.println("------------------------");
        System.out.print("페이지 : ");
        for(int i = 1; i <= pageable.getMaxPageNumber(); i++) {
            if(i == pageable.getPageNumber()) System.out.print( "[" + i + "]" );
            else System.out.print(i);
            if(i != pageable.getMaxPageNumber()) System.out.print(" / ");
            else System.out.println();
        }

    }

    public Map<String, String> extractParam(String cmd) {
        Map<String, String> params = new HashMap<>();

        // 쿼리 문자열을 '&'로 분리
        String[] pairs = cmd.split("&");
        for (String pair : pairs) {
            // '='로 키와 값 분리
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                params.put(key, value);
            } else if (keyValue.length == 1) {
                // 값이 없는 경우
                params.put(keyValue[0], null);
            }
        }
        return params;
    }
}
