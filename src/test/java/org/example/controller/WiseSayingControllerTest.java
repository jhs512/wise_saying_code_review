package org.example.controller;

import org.example.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WiseSayingControllerTest {
    AppConfig appConfig = new AppConfig();

    @Test
    @DisplayName("등록 명령 정상 작동")
    void register_test() {

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        등록
                        명언1
                        사람1
                        종료
                        """
        );

        appConfig.wiseSayingController(scanner).run();
        String result = output.toString().trim();
        System.out.println(result);

        assertTrue(result.contains("명언이 등록되었습니다."));

        TestUtil.clearSetOutToByteArray(output);

    }

    @Test
    @DisplayName("목록 명렁 정상 작동")
    void search_list_test() {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        목록
                        종료
                        """);
        appConfig.wiseSayingController(scanner).run();

        String result = out.toString().trim();
        assertTrue(result.contains("번호 / 작가 / 명언"));
        assertTrue(result.contains("명언1"));
        assertTrue(result.contains("사람1"));

        TestUtil.clearSetOutToByteArray(out);
    }


    @Test
    @DisplayName("삭제 명령 정상 작동")
    void remove() {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        삭제?id=2
                        종료
                        """);
        appConfig.wiseSayingController(scanner).run();
        String result = out.toString().trim();
        assertTrue(result.contains("번 명언이 삭제되었습니다."));
    }

    @Test
    @DisplayName("없는 id 를 입력하였을 때 정상 예외 처리")
    void remove_exception_test() {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        삭제?id=9999999
                        종료
                        """);
        appConfig.wiseSayingController(scanner).run();
        String result = out.toString().trim();
        assertTrue(result.contains("존재하지 않습니다."));
    }

    @Test
    @DisplayName("수정 명령 정상 작동")
    void update_test () {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        수정?id=3
                        수정한 명언입니다
                        수정한 작가입니다
                        목록
                        종료
                        """
        );
        appConfig.wiseSayingController(scanner).run();
        String result = out.toString().trim();
        assertTrue(result.contains("수정한 명언입니다"));
        assertTrue(result.contains("수정한 작가입니다"));
    }

    @Test
    @DisplayName("없는 id 로 수정 시 정상 예외 처리")
    void update_exception_test () {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        수정?id=999999
                        종료
                        """
        );
        appConfig.wiseSayingController(scanner).run();
        String result = out.toString().trim();
        assertTrue(result.contains("존재하지 않습니다."));
    }

    @Test
    @DisplayName("빌드 명령 정상 작동")
    void build_test () {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        빌드
                        종료
                        """
        );
        appConfig.wiseSayingController(scanner).run();
        String result = out.toString().trim();
        assertTrue(result.contains("내용이 갱신되었습니다."));
    }




}