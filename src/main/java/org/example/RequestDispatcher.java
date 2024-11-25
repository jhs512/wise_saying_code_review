package org.example;

import org.example.controller.WiseSayingController;

public class RequestDispatcher {
    WiseSayingController wiseSayingController = new WiseSayingController();

    public void dispatch(String request) {
        if(request.equals("종료")) {
            wiseSayingController.quit();
        }
        else if(request.equals("등록")) {
            wiseSayingController.addWiseSaying();
        }
        else if(request.startsWith("목록")) {
            wiseSayingController.getWiseSayings(request);
        }
        else if(request.startsWith("삭제")) {
            wiseSayingController.deleteWiseSaying(request);
        }
        else if(request.startsWith("수정")) {
            wiseSayingController.updateWiseSaying(request);
        }
        else if(request.equals("빌드")) {
            wiseSayingController.build();
        }
    }

}
