package com.llwiseSaying.Repository;

import com.llwiseSaying.WiseSaying;

import java.util.Map;

public class WiseSayingRepository {

    IdGenerator idGenerator=new IdGenerator();
    WiseSayingGenerator wiseSayingGenerator=new WiseSayingGenerator();
    LoadWiseSayingList loadWiseSayingList=new LoadWiseSayingList();
    DatabaseReset databaseReset=new DatabaseReset();

    public static String DBdirectoryPath = "db/wiseSaying";

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
