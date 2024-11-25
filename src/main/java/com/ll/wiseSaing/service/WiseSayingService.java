package com.ll.wiseSaing.service;

import com.ll.wiseSaing.model.WiseSaying;
import com.ll.wiseSaing.repository.WiseSayingRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private final String CONTENT = "content";
    private final String AUTHOR = "author";
    private final String ID = "id";

    public void getData(List<WiseSaying> list) throws IOException, ParseException {
        JSONArray arr = wiseSayingRepository.selectData();

        if (arr != null) {
            for (Object o : arr) {

                JSONObject obj = (JSONObject) o;
                WiseSaying ws = new WiseSaying();

                ws.setId(Integer.parseInt(String.valueOf(obj.get("id"))));
                ws.setContent(String.valueOf(obj.get("content")));
                ws.setAuthor(String.valueOf(obj.get("author")));

                list.add(ws);
            }
        }

    }

    public void insert(WiseSaying wiseSaying) throws IOException {
        JSONObject obj = new JSONObject();

        obj.put("id", wiseSaying.getId());
        obj.put("content", wiseSaying.getContent());
        obj.put("author", wiseSaying.getAuthor());

        wiseSayingRepository.insertData(obj);
    }

    public void insertLastId(int id) throws IOException {

        wiseSayingRepository.insertLastId(id);
    }

    public void selectList(List<WiseSaying> list, Map<String, String> map) {

        String keywordType = map.get("keywordType");
        String keyword = map.get("keyword");
        List<WiseSaying> tmpList;


        if (CONTENT.equals(keywordType)) {
            tmpList = new ArrayList<>(list).stream()
                    .filter(e -> e.getContent().contains(keyword))
                    .toList();
        } else if (AUTHOR.equals(keywordType)) {
            tmpList = new ArrayList<>(list).stream()
                    .filter(e -> e.getAuthor().contains(keyword))
                    .toList();
        } else {
            tmpList = new ArrayList<>(list);
        }

        int view = 5;
        int paging = (tmpList.size()%view > 0) ? (tmpList.size()/view) + 1: (tmpList.size()/view);
        int page = Integer.parseInt(map.getOrDefault("page", "1"));
        if (page > paging) page = paging;

        int start = (tmpList.size() > 5) ? tmpList.size()-1-((page-1)*view): tmpList.size()-1;
        int end = tmpList.size()-1-(view*page);
        if (end < -1) end = -1;

        for (int i = start; i > end; i--) {
            WiseSaying wiseSaying = tmpList.get(i);
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        System.out.println("----------------------");
        System.out.print("페이지 : ");

        for (int i = 1; i<=paging; i++) {
            if (page == i) System.out.print("[" +i + "]");
            else  System.out.print(i);

            if (i != paging) System.out.print(" / ");
        }
        System.out.println();
    }

    public void deleteData(int id) {

        wiseSayingRepository.deleteData(id);
    }

    public void updateData(WiseSaying wiseSaying) throws IOException {
        JSONObject obj = new JSONObject();

        obj.put("id", wiseSaying.getId());
        obj.put("content", wiseSaying.getContent());
        obj.put("author", wiseSaying.getAuthor());

        wiseSayingRepository.updateData(obj);
    }

    public void build(List<WiseSaying> list) throws IOException {
        JSONArray arr = new JSONArray();

        for (WiseSaying wiseSaying : list) {
            JSONObject obj = new JSONObject();

            obj.put("id", wiseSaying.getId());
            obj.put("content", wiseSaying.getContent());
            obj.put("author", wiseSaying.getAuthor());

            arr.add(obj);
        }

        wiseSayingRepository.build(arr);
    }

    public int getLastId() throws IOException, ParseException {
        return wiseSayingRepository.selectLastId();
    }
}
