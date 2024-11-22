package com.ll.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.domain.WiseSaying;
import com.ll.repository.WiseSayingRepository;
import com.ll.service.cond.SearchCond;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class WiseSayingService {

    private static final Path DATA_PATH = Path.of("").toAbsolutePath().resolve("data");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    private final WiseSayingRepository repository;

    public List<WiseSaying> findAllWiseSaying() {
        return repository.findAll();
    }

    public long addWiseSaying(WiseSaying ws) {
        return repository.addWiseSaying(ws);
    }

    public boolean removeWiseSaying(long deleteId) {
        return repository.removeWiseSaying(deleteId);
    }

    public Optional<WiseSaying> searchById(long searchId) {
        return repository.searchById(searchId);
    }

    public void updateWiseSaying(WiseSaying updateWs) {
        repository.updateWiseSaying(updateWs);
    }

    public List<WiseSaying> searchByCond(SearchCond searchCond) {
        if(searchCond.getType().equals("author")){ return repository.searchByAuthor(searchCond.getKeyword()); }
        else if(searchCond.getType().equals("content")){ return repository.searchByContent(searchCond.getKeyword()); }
        else { return repository.findAll();}
    }

    public boolean buildWiseSaying(){
        List<WiseSaying> wiseSayingList = repository.findAll();
        try{
            Files.createDirectories(DATA_PATH);

            Path dataFile = DATA_PATH.resolve("data.json");
            objectMapper.writeValue(dataFile.toFile(), wiseSayingList);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
