package org.example.service;

import org.example.WiseSaying;
import org.example.repository.WiseSayingRepository;

import java.util.List;
import java.util.Optional;

public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository){
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void flush(){
        wiseSayingRepository.flush();
    }

    public List<WiseSaying> getWiseSayingList(){
        return wiseSayingRepository.getWiseSayingList();
    }

    public int registerWiseSaying(WiseSaying wiseSaying){
        return wiseSayingRepository.registerWiseSaying(wiseSaying);
    }

    public Optional<WiseSaying> getWiseSayingById(int id){
        return wiseSayingRepository.getWiseSayingById(id);
    }

    public void modifyWiseSaying(WiseSaying wiseSaying, String content){
        wiseSayingRepository.modifyWiseSaying(wiseSaying, content);
    }

    public boolean deleteWiseSaying(int id){
        return wiseSayingRepository.deleteWiseSaying(id);
    }



}
