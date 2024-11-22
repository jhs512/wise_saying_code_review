package com.ll.application;

import com.ll.controller.WiseSayingController;
import com.ll.repository.JsonWiseSayingRepository;
import com.ll.repository.WiseSayingRepository;
import com.ll.service.WiseSayingService;

import java.util.Scanner;

public class WiseSayingApp {

    private static final String CMD_EXIT    = "종료";
    private static final String CMD_INSERT  = "등록";
    private static final String CMD_LIST    = "목록";
    private static final String CMD_DELETE  = "삭제";
    private static final String CMD_UPDATE  = "수정";
    private static final String CMD_BUILD   = "빌드";

    private final Scanner sc = new Scanner(System.in);
    private final WiseSayingController controller;

    public WiseSayingApp() {
        WiseSayingRepository repository = new JsonWiseSayingRepository(false);
        WiseSayingService service = new WiseSayingService(repository);
        controller = new WiseSayingController(service);
    }

    public void start() {
        loop();
    }

    private void loop() {
        while(true){
            System.out.print("명령) ");
            String command = sc.nextLine();

            if(!HandleCommand(command)){
                System.out.println("서비스 종료");
                break;
            }
        }
    }

    private boolean HandleCommand(String command){
        if(command.equals(CMD_EXIT)) { return false; }
        else if(command.equals(CMD_INSERT)){ controller.InsertWiseSaying(); }
        else if(command.startsWith(CMD_LIST)){ controller.printBySearch(command); }
        else if(command.startsWith(CMD_DELETE)){ controller.deleteWiseSaying(command); }
        else if(command.startsWith(CMD_UPDATE)){ controller.updateWiseSaying(command); }
        else if(command.equals(CMD_BUILD)) { controller.build(); }
        return true;
    }
}
