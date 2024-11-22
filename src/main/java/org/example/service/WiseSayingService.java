package org.example.service;

import org.example.config.Config;
import org.example.dto.WiseSayingDTO;
import org.example.repository.WiseSayingRepository;

import java.util.List;
import java.util.Map;

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

    public List<WiseSayingDTO> getWiseSayingList(Map<String, String> paramMap) {
        if(paramMap==null) {
            return wiseSayingRepository.findAll();
        }

        String keywordType = paramMap.get("keywordType");
        String keyword = paramMap.get("keyword");

        if(keywordType.equals("content")) {
            return wiseSayingRepository.findByContent(keyword);
        } else if(keywordType.equals("author")) {
            return wiseSayingRepository.findByAuthor(keyword);
        }
        return null;
    }

    public void clearAllWiseSayings() {
        wiseSayingRepository.removeAll();
    }

    public void loadData() {
        wiseSayingRepository.load();
    }
}
