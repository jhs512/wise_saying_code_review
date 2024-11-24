package org.example.controller;

import org.example.App;
import org.example.config.Config;
import org.example.dto.Pageable;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;
import org.example.service.WiseSayingService;

import java.util.*;

public class WiseSayingController {
    WiseSayingService wiseSayingService = new WiseSayingService();
    static private Scanner scanner;

    private Map<String, String> parseRequestParams(String params) {
        Map<String, String> paramMap = new HashMap<>();
        String[] paramArr = params.split("&");

        for (String param : paramArr) {
            String[] kv = param.split("=");
            paramMap.put(kv[0],kv[1]);
        }
        return paramMap;
    }

    private Map<String, String> getRequestParams(String request) {
        Map<String, String> paramMap = new HashMap<>();

        String[] parseArr = request.split("\\?");
        if(parseArr.length!=2)
            return null; // 이부분 나중에는 throw로 가자

        String requestParams = parseArr[1];

        return parseRequestParams(requestParams);
    }

    public void build() {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void quit() {
        return;
    }

    public void addWiseSaying() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        int id = wiseSayingService.add(content, author);
        System.out.printf("%d번 명언이 등록되었습니다.%n",id);
    }

    public void getWiseSayings(String request) {
        Map<String, String> paramMap = getRequestParams(request);
        Pageable pageable = new Pageable();
        List<WiseSayingDTO> list = wiseSayingService.getWiseSayingList(paramMap, pageable);
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for(int i=list.size()-1; i>=0; i--) {
            WiseSayingDTO wiseSayingDTO = list.get(i);
            System.out.println(wiseSayingDTO.toString());
        }
    }


    public void deleteWiseSaying(String request) {
        String id = getRequestParams(request).get("id");
        if(id==null) {
            return;
        }

        WiseSayingDTO deleted = wiseSayingService.delete(Integer.parseInt(id));

        if(deleted == null) {
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        } else {
            System.out.println(id+"번 명언이 삭제되었습니다.");
        }
    }

    public void updateWiseSaying(String request) {
        String id = getRequestParams(request).get("id");
        if(id==null) {
            return;
        }

        WiseSayingDTO wiseSayingDTO = getWiseSaying(Integer.parseInt(id));
        if(wiseSayingDTO == null) {
            System.out.println(id+"번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + wiseSayingDTO.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.println("작가(기존) : " + wiseSayingDTO.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        wiseSayingDTO.setContent(content);
        wiseSayingDTO.setAuthor(author);

        wiseSayingService.update(wiseSayingDTO);
    }

    private WiseSayingDTO getWiseSaying(int id) {
        return wiseSayingService.findById(id);
    }

    public void bindingConfig(Config config) {
        wiseSayingService.bindingConfig(config);
    }

    public void deleteAllWiseSayings() {
        wiseSayingService.clearAllWiseSayings();
    }

    public void setScanner(Scanner scanner) {
        WiseSayingController.scanner=scanner;
    }

    public void loadData() {
        wiseSayingService.loadData();
    }
}
