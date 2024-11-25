package org.example.main;

import org.example.controller.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WiseSayingApp {
    private final List<Controller> controllerList = new ArrayList<>();

    public WiseSayingApp(Controller ... controllers){
        controllerList.addAll(Arrays.asList(controllers));
    }

    public void run(){
        for (Controller controller : controllerList){
            controller.run();
        }
    }
}
