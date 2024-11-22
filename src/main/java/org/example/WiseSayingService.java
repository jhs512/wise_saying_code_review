package org.example;

import java.io.*;
import java.util.ArrayList;

public class WiseSayingService {
    private int id;

    private WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public WiseSayingService() {
    }

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    public void atStartRun() throws IOException {
        wiseSayingRepository.getAllDB();
        this.id = wiseSayingRepository.getLastId();
    }

    public int registWiseSaying(String wiseSaying, String writter) {
        try {
            wiseSayingRepository.saveWiseSaying(new WiseSaying(id, wiseSaying, writter));

            id++;
            wiseSayingRepository.setLastId(id);
        } catch(IOException e) {
            System.out.println(" = " + e);
        }
        return id - 1;
    }

    public ArrayList<WiseSaying> viewPagedWiseSayings(Pageable pageable) {
        return wiseSayingRepository.findPagedWiseSayings(pageable);
    }

    public void removeWiseSaying(int id) {
        wiseSayingRepository.removeById(id);
    }

    public void updateWiseSaying(int id, String updateSaying, String updateWritter) throws IOException {
        wiseSayingRepository.updateWiseSaying(id, updateSaying, updateWritter);
    }

    public WiseSaying findWiseSayingById(int id) {
        return wiseSayingRepository.findById(id);
    }

    public void buildData() throws IOException {
        wiseSayingRepository.buildData();
    }

    public ArrayList<WiseSaying> searchWiseSaying(String keyword, String type, Pageable pageable) throws IOException {
        ArrayList<WiseSaying> result;
        if(type.equals("content")) {
            result = wiseSayingRepository.serarchWiseSayingsBySaying(keyword, pageable);
        }
        else if(type.equals("author")) {
            result = wiseSayingRepository.serarchWiseSayingsByWritter(keyword, pageable);
        }
        else throw new IOException();

        return result;
    }
}
