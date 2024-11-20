package org.example.controller;

import org.example.App;
import org.example.config.Config;
import org.example.dto.WiseSayingDTO;
import org.example.service.WiseSayingService;
import org.example.view.TerminalView;

public class WiseSayingController {
    WiseSayingService wiseSayingService = new WiseSayingService();
    TerminalView terminalView = new TerminalView();

    public void build() {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void quit() {
        System.exit(0);
    }

    public void addWiseSaying(WiseSayingDTO model) {
        String content = model.getContent();
        String author = model.getAuthor();

        int id = wiseSayingService.add(content, author);
        System.out.printf("%d번 명언이 등록되었습니다.%n",id);
    }

    public void getWiseSayingList() {
        App.showWiseSayingList();
    }


    public void deleteWiseSaying(int id) {
        int status = wiseSayingService.delete(id);

        if(status == 0) {
            System.out.println(id+"번 명언이 삭제되었습니다.");
        } else if(status == -1) {
            System.out.println(id+"번 명언은 존재하지 않습니다.");
        }
    }

    public void updateWiseSaying(WiseSayingDTO model) {
        wiseSayingService.update(model);
    }

    public WiseSayingDTO getWiseSaying(int id) {
        return wiseSayingService.findById(id);
    }

    public void postConstruct(Config config) {
        wiseSayingService.postConstruct(config);
    }
}
