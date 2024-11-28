package wisesaying_tdd.wisesaying.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import wisesaying_tdd.WiseSaying;
import wisesaying_tdd.wisesaying.Service.WiseSayingServiceImpl;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingServiceImpl wiseSayingService;
    private List<WiseSaying> controllerList;
    String msg;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
        this.wiseSayingService = new WiseSayingServiceImpl();
    }

    public int GetId(String cmd) {
        int id = 0; 
        id = wiseSayingService.GetId(cmd);

        return id;
    }

    public String AddWiseSaying(String cmd, int id, String wiseSaying, String authur) {
        msg = wiseSayingService.AddWiseSaying(cmd, id,wiseSaying,authur);

        return msg;
        
    }
    
    public List<WiseSaying> ShowWiseSaying(String cmd) {
        controllerList = new ArrayList<>();
        controllerList = wiseSayingService.ShowWiseSayingList(cmd);
        return controllerList;

    }

    public String DeleteWiseSaying(String cmd,int targerId) {
        msg = wiseSayingService.DeleteWiseSaying(cmd,targerId);

        return msg;
    }

    public String BuildWiseSaying(String cmd) {
        msg = wiseSayingService.BuildWiseSaying(cmd);

        return msg;
    }
}
