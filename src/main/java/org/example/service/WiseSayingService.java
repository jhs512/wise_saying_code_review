package org.example.service;

import org.example.config.Config;
import org.example.dto.WiseSayingDTO;
import org.example.entity.WiseSaying;
import org.example.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public int add(String content, String author) {
        return wiseSayingRepository.save(content, author);
    }

    public WiseSayingDTO delete(int id) {
        return wiseSayingRepository.remove(id);
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

    public void bindingConfig(Config config) {
        wiseSayingRepository.setConfig(config);
    }

    public List<WiseSayingDTO> getWiseSayingList() {
        return wiseSayingRepository.findAll();
    }

    public void clearAllWiseSayings() {
        wiseSayingRepository.removeAll();
    }

    public void loadData() {
        wiseSayingRepository.load();
    }
}
