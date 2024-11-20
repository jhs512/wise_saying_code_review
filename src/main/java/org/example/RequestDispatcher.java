package org.example;

import org.example.controller.WiseSayingController;
import org.example.dto.WiseSayingDTO;
import org.example.view.TerminalView;

public class RequestDispatcher {
    WiseSayingController wiseSayingController = new WiseSayingController();
    TerminalView terminalView = new TerminalView();

    public void dispatch(String request) {
        if(request.equals("종료")) {
            wiseSayingController.quit();
            System.exit(0);
        }
        else if(request.equals("등록")) {
            // View
            WiseSayingDTO model = new WiseSayingDTO();
            terminalView.add(model);

            // Dispatch
            wiseSayingController.addWiseSaying(model);
        }
        else if(request.equals("목록")) {
            wiseSayingController.getWiseSayingList();
        }
        else if(request.startsWith("삭제")) {
            String[] requestArr = request.split("id=");
            if(requestArr.length != 2) return;

            int delete_id = Integer.parseInt(requestArr[1]);
            wiseSayingController.deleteWiseSaying(delete_id);
        }
        else if(request.startsWith("수정")) {
            String[] requestArr = request.split("id=");
            if(requestArr.length != 2) return;
            int update_id = Integer.parseInt(requestArr[1]);

            // view
            WiseSayingDTO model = wiseSayingController.getWiseSaying(update_id);
            if(model == null) {
                System.out.println(update_id+"번 명언은 존재하지 않습니다.");
                return;
            }
            terminalView.update(model);
            wiseSayingController.updateWiseSaying(model);
        }
        else if(request.equals("빌드")) {
            wiseSayingController.build();
        }
    }

}
