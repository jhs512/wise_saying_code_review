package com.ll.wiseSaing.controller;

import com.ll.wiseSaing.model.WiseSaying;
import com.ll.wiseSaing.service.WiseSayingService;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class WiseSayingController{

    private final Scanner sc = new Scanner(System.in);

    private static int id;
    private static List<WiseSaying> list = new ArrayList<>();

    private final WiseSayingService wiseSayingService = new WiseSayingService();

    public void insert() throws IOException {
        WiseSaying WiseSaying = new WiseSaying();

        System.out.print("명언 : ");
        WiseSaying.setContent(sc.nextLine());

        System.out.print("작가 : ");
        WiseSaying.setAuthor(sc.nextLine());

        WiseSaying.setId(++id);

        list.add(WiseSaying);

        wiseSayingService.insert(WiseSaying);

        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void selectList(String command) {
        Map<String, String> map = getSearch(command);

        if (map.get("keywordType") != null && map.get("keyword") != null) {
            System.out.println("----------------------");
            System.out.println("검색타입 : " + map.get("keywordType"));
            System.out.println("검색어 : " + map.get("keyword"));
        }

//        String rule = sc.nextLine();
//        String[] rules = rule.split("/");
        System.out.println("----------------------");
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        if (!list.isEmpty()) wiseSayingService.selectList(list, map);
    }

    public void delete(String command) {
        if(!command.contains("?id=")) return;
        int delete_id = Integer.parseInt(command.split("=")[1]);

        boolean flag = false;
        int index = 0;

        for (int i = 0; i<list.size(); i++) {
            WiseSaying wiseSaying = list.get(i);
            if (wiseSaying.getId() == delete_id) {
                index = i;
                flag = true;
                break;
            }
        }

        if (flag) {
            list.remove(index);
            wiseSayingService.deleteData(delete_id);
            System.out.println(delete_id + "번 명언이 삭제되었습니다.");
        }
        else {
            System.out.println(delete_id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void modify(String command) throws IOException {
        if(!command.contains("?id=")) return;
        int modify_id = Integer.parseInt(command.split("=")[1]);

        WiseSaying wiseSaying = new WiseSaying();
        int idx = 0;

        for (int i = 0; i<list.size(); i++) {
            WiseSaying tmp = list.get(i);
            if (tmp.getId() == modify_id) {
                wiseSaying = tmp;
                idx = i;
                break;
            }
        }

        if (wiseSaying.getId() != 0) {
            wiseSaying.setId(modify_id);

            System.out.println("명언(기존) : " + wiseSaying.getContent());
            System.out.print("명언 : ");
            wiseSaying.setContent(sc.nextLine());

            System.out.println("작가(기존) : " + wiseSaying.getAuthor());
            System.out.print("작가 : ");
            wiseSaying.setAuthor(sc.nextLine());

            list.set(idx, wiseSaying);
            wiseSayingService.updateData(wiseSaying);
        }
    }

    public void build() throws IOException {
        wiseSayingService.build(list);
    }

    public void end() throws IOException, ParseException {
        wiseSayingService.insertLastId(id);
    }

    private static Map<String, String> getSearch(String command) {
        Map<String, String> map = new HashMap<>();

        if (command.contains("?")) {
            String search = command.split("\\?")[1];
            String[] keys = search.split("&");

            for (String key : keys) {
                String[] keyValue = key.split("=");
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }
    public void init() throws IOException, ParseException {
        wiseSayingService.getData(list);
        id = wiseSayingService.getLastId();
    }
}
