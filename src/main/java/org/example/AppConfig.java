package org.example;

import org.example.controller.WiseSayingController;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

public class AppConfig {

    public WiseSayingRepository wiseSayingRepository() {
        return new WiseSayingRepository();
    }

    public WiseSayingService wiseSayingService() {
        return new WiseSayingService(wiseSayingRepository());
    }

    public WiseSayingController wiseSayingController() {
        return new WiseSayingController(wiseSayingService());
    }
}
