package org.example.service;

import org.example.App;
import org.example.config.Config;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;
import org.example.repository.WiseSayingRepository;

import java.util.Scanner;

public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private final Scanner scanner = new Scanner(System.in);

    public int add(String content, String author) {
        return wiseSayingRepository.save(content, author);
    }

    public int delete(int id) {
        for(int i = 0; i< App.wiseSayingList.size(); i++) {
            WiseSaying ws = App.wiseSayingList.get(i);
            if(ws.getId() == id) {
                wiseSayingRepository.remove(i);
                return 0;
            }
        }
        return -1;
    }

    public void update(WiseSayingDTO wiseSayingDTO) {
        wiseSayingRepository.update(wiseSayingDTO);
    }

    public void build() {
        wiseSayingRepository.build();
    }

    public WiseSayingDTO findById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void postConstruct(Config config) {
        wiseSayingRepository.init(config);
        wiseSayingRepository.load();
    }
}
