package com.ll.wiseSaing.controller;

import com.ll.wiseSaing.model.WiseSaying;
import com.ll.wiseSaing.service.WiseSayingService;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WiseSayingController{

    Scanner sc = new Scanner(System.in);

    File file;
    BufferedWriter bw;

    WiseSayingService wiseSayingService = new WiseSayingService();

    public void init(int id, List<WiseSaying> list) throws IOException, ParseException {
        wiseSayingService.init(list, id);
    }

    public void insert(int id, List<WiseSaying> list) throws IOException {
        WiseSaying WiseSaying = new WiseSaying();

        System.out.print("명언 : ");
        WiseSaying.setContent(sc.nextLine());

        System.out.print("작가 : ");
        WiseSaying.setAuthor(sc.nextLine());

        WiseSaying.setId(id);

        list.add(WiseSaying);

        wiseSayingService.insert(WiseSaying);

        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    public void selectList(List<WiseSaying> list) {
        String rule = sc.nextLine();
        String[] rules = rule.split("/");
        System.out.println("----------------------");

        wiseSayingService.selectList(list, rules);
    }

    public void delete(int id, List<WiseSaying> list) {
        boolean flag = false;
        int index = 0;

        for (int i = 0; i<list.size(); i++) {
            WiseSaying wiseSaying = list.get(i);
            if (wiseSaying.getId() == id) {
                index = i;
                flag = true;
                break;
            }
        }

        if (flag) {
            list.remove(index);
            wiseSayingService.deleteData(id);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        }
        else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void modify(int id, List<WiseSaying> list) throws IOException {
        WiseSaying wiseSaying = new WiseSaying();
        int idx = 0;

        for (int i = 0; i<list.size(); i++) {
            WiseSaying tmp = list.get(i);
            if (tmp.getId() == id) {
                wiseSaying = tmp;
                idx = i;
                break;
            }
        }

        if (wiseSaying.getId() != 0) {
            wiseSaying.setId(id);

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

    public void build(List<WiseSaying> list) throws IOException {
        wiseSayingService.build(list);
    }

    public void end(int id) throws IOException, ParseException {
        wiseSayingService.insertLastId(id);
    }
}
