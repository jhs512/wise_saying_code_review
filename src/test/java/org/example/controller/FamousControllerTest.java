package org.example.controller;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class FamousControllerTest {

    @Test
    void addFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                등록
                명언1
                작가1
                종료
                """));

        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("명언이 등록되었습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void printFamousSayings() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                목록
                종료
                """));

        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("""
                번호 / 작가 / 명언
                ----------------
                """));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void updateFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                수정?id=13
                명언2
                작가2
                종료
                """));
        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("명언이 수정되었습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void updateFailedFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                수정?id=13
                명언2
                작가2
                종료
                """));
        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("명언이 존재하지 않습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void deleteFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                삭제?id=13
                종료
                """));
        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("명언을 삭제하였습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void deleteFailFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                삭제?id=13
                종료
                """));
        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("명언이 존재하지 않습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void buildFamousSaying() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        FamousController famousController = new FamousController(TestUtil.genScanner("""
                빌드
                종료
                """));
        famousController.run();

        String result = output.toString().trim();

        assertTrue(result.contains("data.json 파일의 내용이 갱신되었습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }
}