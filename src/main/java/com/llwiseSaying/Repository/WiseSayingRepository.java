package com.llwiseSaying.Repository;

import com.llwiseSaying.WiseSaying;
import com.llwiseSaying.Config.Config;
import com.llwiseSaying.Config.DatabaseConfig;

import java.util.Map;

public class WiseSayingRepository {



    Config config;
    IdGenerator idGenerator;
    WiseSayingGenerator wiseSayingGenerator;
    LoadWiseSayingList loadWiseSayingList;
    DatabaseReset databaseReset;

    public WiseSayingRepository() {
        this.config=new DatabaseConfig();
        idGenerator=new IdGenerator(config);
        wiseSayingGenerator=new WiseSayingGenerator(config);
        loadWiseSayingList=new LoadWiseSayingList(config);
        databaseReset=new DatabaseReset(config);
    }

    public void setConfig(Config config) {
        this.config=config;
    }

    ///////////////////////////////////////////////////////

    public int loadId() {

        int id=idGenerator.loadFile();

        return id;
    }

    public Map<Integer, WiseSaying> loadWiseSays() {
        return loadWiseSayingList.loadWiseSayings();
    }

    ///////////////////////////////////////////////////////

    public void makeWiseSayingFile(WiseSaying wiseSaying) {
        //수정과 생성 같이 사용한다.
        wiseSayingGenerator.wirteFile(wiseSaying);
    }

    public void deleteWiseSayingFile(int id) {
        wiseSayingGenerator.deleteFile(id);
    }

    public void reset() {
        databaseReset.reset();
    }

    public void saveId(int id) {
        idGenerator.makeFile(id);
    }

}
