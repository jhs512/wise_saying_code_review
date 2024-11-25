package org.example;

import org.example.controller.WiseSayingController;
import org.example.repository.WiseSayingRepository;
import org.example.service.WiseSayingService;

import java.util.Scanner;

public class AppConfig {

    private final String filePath;

    public AppConfig(String filePath) {
        this.filePath = filePath;
    }

    public WiseSayingRepository wiseSayingRepository() {
        return new WiseSayingRepository(filePath);
    }

    public WiseSayingService wiseSayingService() {
        return new WiseSayingService(wiseSayingRepository());
    }

    public WiseSayingController wiseSayingController(Scanner scanner) {
        return new WiseSayingController(wiseSayingService(), scanner);
    }
}
