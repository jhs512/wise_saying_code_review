package baekgwa.controller;

import static org.junit.jupiter.api.Assertions.*;

import baekgwa.dto.RequestDto;
import baekgwa.supporter.ControllerTestSupporter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WiseSayingControllerTest extends ControllerTestSupporter {

    @Disabled
    @DisplayName("폴더 경로를 생성합니다.")
    @Test
    void createDirectories() {
    }

    @DisplayName("새로운 명언을 등록합니다. 등록 후, 결과를 출력합니다.")
    @Test
    void registerTest() {
        //init
        initInput("""
        새로운 명언
        작가 강성욱
        """);
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given
        RequestDto.Register input =
                new RequestDto.Register(baseWiseSaying.getAuthor(), baseWiseSaying.getContent());

        try {
            // when
            wiseSayingController.register();

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("1번 명언이 등록되었습니다."));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("명언을 모두 출력합니다.")
    @Test
    void printAllTest() {
        //init
        initController();
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given

        try {
            // when
            wiseSayingController.printAll();

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("----------------------"));
            assertTrue(result.contains("1 / author / content"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("존재중인 명언을 Id를 통해 삭제합니다.")
    @Test
    void deleteTest() {
        //init
        initController();
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given

        try {
            // when
            wiseSayingController.delete(baseWiseSaying.getId());

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("1번 명언이 삭제되었습니다."));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("없는 명언 ID로 삭제하면, 메세지를 출력하고 삭제에 실패합니다.")
    @Test
    void deleteTest2() {
        //init
        initController();
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given

        try {
            // when
            wiseSayingController.delete(-1L);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("번 명언은 존재하지 않습니다."));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("명언을 수정합니다.")
    @Test
    void modifyWiseSayingTest() {
        //init
        initInput("명언 수정\n작가 수정\n");
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given

        try {
            // when
            wiseSayingController.modifyWiseSaying(baseWiseSaying.getId());

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("명언(기존) : " + baseWiseSaying.getContent()));
            assertTrue(result.contains("작가(기존) : " + baseWiseSaying.getAuthor()));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("없는 명언을 수정하려고 하면, 오류 메세지를 반환하고 작업이 취소됩니다.")
    @Test
    void modifyWiseSayingTest2() {
        //init
        initInput("명언 수정\n작가 수정\n");
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initOutput(outputStream);

        // given

        try {
            // when
            wiseSayingController.modifyWiseSaying(-1L);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("-1번 명언은 존재하지 않습니다."));
        } finally {
            afterDetach(originalOut);
        }
    }

    @Disabled
    @DisplayName("Build 를 진행합니다. 빌드는 모든 명언 데이터를 data.json 으로 말아줍니다.")
    @Test
    void buildTest() {
    }
}