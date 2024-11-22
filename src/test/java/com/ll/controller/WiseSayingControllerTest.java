package com.ll.controller;

import com.ll.domain.WiseSaying;
import com.ll.repository.JsonWiseSayingRepository;
import com.ll.repository.WiseSayingRepository;
import com.ll.service.WiseSayingService;
import com.ll.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

class WiseSayingControllerTest {

    private ByteArrayOutputStream outContent;
    private WiseSayingController controller;
    private WiseSayingService service;
    private WiseSayingRepository repository;

    @BeforeEach
    void before(){
        outContent = TestUtil.setOutToByteArray(); // System.out을 ByteArrayOutputStream으로 리다이렉트
        repository = new JsonWiseSayingRepository(true);
        service = new WiseSayingService(repository);
    }

    void dummySample(){

        // 테스트를 위해서 Repository 에 바로 생성 (등록 테스트는 별도로 진행)
        for(int i = 1; i <= 20; i++){
            WiseSaying ws = new WiseSaying();
            ws.setAuthor("작가" + i);
            ws.setContent("명언" + i);
            repository.addWiseSaying(ws);
        }
    }

    @Test
    void 등록테스트(){

        // setting
        String testInput = "1\n1\n";
        Scanner scanner = TestUtil.genScanner(testInput);
        controller = new WiseSayingController(scanner, service);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        // data check
        long id = controller.InsertWiseSaying();
        Assertions.assertEquals(service.searchById(id).get().getAuthor(), "1");
        Assertions.assertEquals(service.searchById(id).get().getContent(), "1");

        // output check
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("명언 :"));
        Assertions.assertTrue(output.contains("작가 :"));
        Assertions.assertTrue(output.contains(id + "번 명언이 등록되었습니다."));

        // clear
        TestUtil.clearSetOutToByteArray(outputStream);
        scanner.close();
    }

    @Test
    void 목록테스트(){

        // setting
        dummySample();
        controller = new WiseSayingController(service);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        // test
        controller.printBySearch("목록?keywordType=content&keyword=1");
        controller.printBySearch("목록?keywordType=author&keyword=5");

        // output check
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("명언1"));
        Assertions.assertTrue(output.contains("명언10"));
        Assertions.assertTrue(output.contains("작가5"));

        // clear
        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    void 삭제테스트(){

        // setting
        dummySample();
        controller = new WiseSayingController(service);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        // test
        controller.deleteWiseSaying("삭제?id=1");
        controller.deleteWiseSaying("삭제?id=1");
        controller.deleteWiseSaying("삭제?id=5");

        // output check
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("1번 명언이 삭제되었습니다."));
        Assertions.assertTrue(output.contains("1번 명언은 존재하지 않습니다."));
        Assertions.assertTrue(output.contains("5번 명언이 삭제되었습니다."));

        // clear
        TestUtil.clearSetOutToByteArray(outputStream);
    }

    @Test
    void 수정테스트(){

        // setting
        dummySample();
        String testInput = "새로운명언\n새로운이름\n";
        Scanner scanner = TestUtil.genScanner(testInput);
        controller = new WiseSayingController(scanner, service);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        // test
        long updateId1 = controller.updateWiseSaying("수정?id=11");
        long updateId2 = controller.updateWiseSaying("수정?id=1");
        Assertions.assertEquals(updateId1, 0);
        Assertions.assertEquals(updateId2, 1);

        // output test
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("11번 명언은 존재하지 않습니다."));
        Assertions.assertTrue(output.contains("명언(기존) : 명언1"));
        Assertions.assertTrue(output.contains("작가(기존) : 작가1"));

        // clear
        TestUtil.clearSetOutToByteArray(outputStream);
        scanner.close();
    }

    @Test
    void 페이징테스트(){

        // setting
        dummySample();
        controller = new WiseSayingController(service);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        controller.printBySearch("목록");
        controller.printBySearch("목록?page=2");

        // output test
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains(
                        "20 / 작가20 / 명언20\r\n" +
                        "19 / 작가19 / 명언19\r\n" +
                        "18 / 작가18 / 명언18\r\n" +
                        "17 / 작가17 / 명언17\r\n" +
                        "16 / 작가16 / 명언16\r\n" +
                        "------------------\r\n" +
                        "페이지 :  [1] 2 3 4"));

        Assertions.assertTrue(output.contains(
                        "15 / 작가15 / 명언15\r\n" +
                        "14 / 작가14 / 명언14\r\n" +
                        "13 / 작가13 / 명언13\r\n" +
                        "12 / 작가12 / 명언12\r\n" +
                        "11 / 작가11 / 명언11\r\n" +
                        "------------------\r\n" +
                        "페이지 :  1 [2] 3 4"));
        // clear
        TestUtil.clearSetOutToByteArray(outputStream);
    }
}