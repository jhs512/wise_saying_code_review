package org.example;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository repository;
    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }

    public int registerWiseSaying(String saying, String writer) {
        return repository.save(new WiseSaying(saying,writer));
    }
    public List<WiseSaying> listWiseSayings(int page){
        return repository.findAllPaged(page);
    }

    public boolean deleteWiseSaying(int id) {
        return repository.deleteById(id);
    }

    public boolean updateWiseSaying(int id, String saying, String writer) {
        return repository.updateById(id,saying,writer);
    }

    public boolean buildDataJson() {
        return repository.buildDataJson();
    }

    public int getTotalPages(){
        return repository.getTotalPages();
    }
}
