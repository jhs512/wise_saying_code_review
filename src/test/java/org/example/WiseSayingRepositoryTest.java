package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class WiseSayingRepositoryTest {
    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    String dbDir = ".\\src\\test\\db\\wiseSaying\\";

    private WiseSaying testWS1 = new WiseSaying(100, "테스트명언", "테스트작가");
    private WiseSaying testWS2 = new WiseSaying(101, "테스트명언2", "테스트작가2");
    private Pageable pageable = new Pageable(1);
    private File testFile = new File(dbDir + "100.json");
    private File testFile2 = new File(dbDir + "101.json");
    private File lastID = new File(dbDir + "lastId.txt");

    @BeforeEach
    void BeforeEach() throws IOException {
        wiseSayingRepository.setDbDir(dbDir);
        wiseSayingRepository.setLastId(102);

        wiseSayingRepository.saveWiseSaying(testWS1);
        wiseSayingRepository.saveWiseSaying(testWS2);
    }

    @AfterEach
    void AfterEach() {
        System.gc();
        File[] testFiles = new File(dbDir).listFiles();
        for (File tFs : testFiles) {
            tFs.delete();
        }
    }

    @Test
    void getAllDB() throws IOException {
        wiseSayingRepository.getAllDB();    // BeforeEach에서 2개 파일 저장된 상태에서 그 파일 2개 다시 DB에 저장됨
        assertEquals(4, wiseSayingRepository.DB.size());
    }

    @Test
    void setANDGetLastId() throws IOException {
        wiseSayingRepository.setLastId(101);
        assertEquals(wiseSayingRepository.getLastId(), 101);
    }

    @Test
    void saveWiseSaying() {
        assert testFile.exists();
    }

    @Test
    void findPagedWiseSayings() {
        Pageable pageable = new Pageable(1);
        assertEquals(wiseSayingRepository.findPagedWiseSayings(pageable).size(), 2);
    }

    @Test
    void findById() {
        assertEquals(wiseSayingRepository.findById(100).getSaying(), "테스트명언");
        assertEquals(wiseSayingRepository.findById(101).getWritter(), "테스트작가2");
    }

    @Test
    void removeById() throws IOException {
        wiseSayingRepository.saveWiseSaying(new WiseSaying(200, "삭제", "제삭"));
        wiseSayingRepository.removeById(200);
        assertFalse(new File(dbDir + "200.json").exists());
    }

    @Test
    void updateWiseSaying() throws IOException {
        wiseSayingRepository.updateWiseSaying(100, "수정명언", "수정작가");
        assertEquals(wiseSayingRepository.findById(100).getSaying(), "수정명언");
    }

    @Test
    void searchWiseSayingsBySaying() {
        WiseSaying result = wiseSayingRepository.serarchWiseSayingsBySaying("언2", pageable).getFirst();
        assertEquals("테스트명언2", result.getSaying());
    }

    @Test
    void searchWiseSayingsByWritter() {
        WiseSaying result = wiseSayingRepository.serarchWiseSayingsByWritter("가2", pageable).getFirst();
        assertEquals("테스트작가2", result.getWritter());
    }

    @Test
    void pageNation() throws IOException {
        for(int i = 0; i < 20; i++) {
            wiseSayingRepository.saveWiseSaying(new WiseSaying(i, i+"", i+""));
        }
        /*
        19  14  9   4   101
        18  13  8   3   100
        17  12  7   2
        16  11  6   1
        15  10  5   0
         */

        assertEquals(wiseSayingRepository.findPagedWiseSayings(new Pageable(3)).get(2).getId(), 7);
        assertEquals(wiseSayingRepository.findPagedWiseSayings(new Pageable(5)).size(), 2);
    }

}