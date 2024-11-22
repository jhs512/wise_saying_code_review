package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.example.Main.App;
import org.example.config.ConfigReader;
import org.example.dto.WiseSaying;
import org.example.service.WiseSayingService;
import org.example.config.DependencyContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {

    private final WiseSayingService wiseSayingService;
    private final ConfigReader configReader;

    MainTest() {
        DependencyContainer dependencyContainer = new DependencyContainer();
        this.wiseSayingService = dependencyContainer.createWiseSayingService();
        this.configReader = new ConfigReader();
    }


    @AfterEach
    public void cleanUp() throws IOException {
        // 디렉토리 삭제 전에 파일이나 디렉토리 내용이 있다면 삭제
        File directory = new File(configReader.getProperty("test.save.path"));
        if (directory.exists()) {
            // 디렉토리 내 모든 파일 삭제
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // 재귀적으로 디렉토리 삭제
                } else {
                    if (file.delete()) {
                        System.out.println("Deleted " + file.getName());
                    } else {
                        throw new IOException("Failed to delete " + file.getName());
                    }
                }
            }
            if (directory.delete()) {
                System.out.println("Deleted " + directory.getName());
            } else {
                throw new IOException("Failed to delete " + directory.getName());
            }

        }
    }

    // 디렉토리 및 그 안의 파일을 삭제하는 메서드
    private void deleteDirectory(File directory) throws IOException {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                deleteDirectory(file); // 재귀적으로 디렉토리 삭제
            } else {
                if (file.delete()) {
                    System.out.println("Deleted " + file.getName());
                } else {
                    throw new IOException("Failed to delete " + file.getName());
                }
            }
        }
        if (directory.delete()) {
            System.out.println("Deleted " + directory.getName());
        } else {
            throw new IOException("Failed to delete " + directory.getName());
        }
    }

    @Test
    @DisplayName("앱 종료 테스트")
    public void appExitTest() throws Exception {

        // given
        String cmd = """
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result).contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 등록 테스트")
    public void createWiseSayingTest() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 목록 테스트 - 파일이 없을때")
    public void getAllWiseSayingTest1() throws Exception {

        // given
        String cmd = """
            목록
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------")
            .contains("명령) ")
            .contains("페이지 : [1]")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 목록 테스트2 - 파일이 1개 일때")
    public void getAllWiseSayingTest2() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            목록
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListOfWiseSaying(1);
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : [1]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 목록 테스트3 - 파일이 10개 이상 일때")
    public void getAllWiseSayingTest3() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            목록
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListOfWiseSaying(1);
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : [1] / 2")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 목록 테스트4 - 파일이 10개 이상 일때 2페이지")
    public void getAllWiseSayingTest4() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            목록?page=2
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListOfWiseSaying(2);
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : 1 / [2]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 목록 테스트5 - 파일이 6개 일때 2페이지")
    public void getAllWiseSayingTest5() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            등록
            현재를 사랑하라.
            작자미상
            목록?page=2
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListOfWiseSaying(2);
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : 1 / [2]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 검색 목록 테스트 - 검색, 페이지가 주어지지 않았을때")
    public void getListByKeyword() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")
            .contains("----------------------")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListByKeyword("content", "과거", 1);
        if (!listOfWiseSaying.isEmpty()) {
            listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
            for (WiseSaying wiseSaying : listOfWiseSaying) {
                assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
        }

        assertThat(result)
            .contains("페이지 : [1]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 검색 목록 테스트2 - 검색 파일이 없을때")
    public void getListByKeyword2() throws Exception {

        // given
        String cmd = """
            목록?keywordType=content&keyword=과거
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("명령) ")
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")
            .contains("----------------------")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListByKeyword("content", "과거", 1);
        listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : [1]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 검색 목록 테스트3 - 검색 결과 5개 이상일 때 1페이지")
    public void getListByKeyword3() throws Exception {

        // given
        String cmd = """
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거&page=1
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")
            .contains("----------------------")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListByKeyword("content", "과거", 1);
        listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
        for (WiseSaying wiseSaying : listOfWiseSaying) {
            assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
        }

        assertThat(result)
            .contains("페이지 : [1]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 검색 목록 테스트4 - 검색 결과 5개 이상일 때 2페이지")
    public void getListByKeyword4() throws Exception {

        // given
        String cmd = """
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거&page=2
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")
            .contains("----------------------")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------")
            .contains("페이지 : 1 / [2]")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 삭제 테스트 - 성공")
    public void deleteWiseSayingTest() throws Exception {
        //given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            삭제?id=1
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        //then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("1번 명언이 삭제되었습니다.")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 삭제 테스트 - 실패(명언이 존재하지 않을 때)")
    public void deleteWiseSayingTest2() throws Exception {
        //given
        String cmd = """
            삭제?id=1
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        //then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("번 명언은 존재하지 않습니다.")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 수정 테스트 - 성공")
    public void updateWiseSayingTest() throws Exception {
        //given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            수정?id=1
            과거에 집착하지 마라.
            작자미상2
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("1번 명언이 등록되었습니다.")
            .contains("명령) ")
            .contains("명언(기존) : 현재를 사랑하라")
            .contains("작가(기존) : 작자미상")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("명언 수정 테스트 - 실패(명언이 존재하지 않을 때)")
    public void updateWiseSayingTes2t() throws Exception {
        //given
        String cmd = """
            수정?id=1
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        //then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("번 명언은 존재하지 않습니다.")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("빌드파일 생성 테스트")
    public void createBuildFileTest() throws Exception {
        //given
        String cmd = """
            빌드
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        //then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("data.json 파일의 내용이 갱신되었습니다.")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("빌드파일 생성 테스트2")
    public void createBuildFileTest2() throws Exception {
        //given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            빌드
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        //when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        //then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("명령) ")
            .contains("data.json 파일의 내용이 갱신되었습니다.")
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        TestUtil.clearSetOutToByteArray(output);
    }


    @Test
    @DisplayName("명언 목록 테스트 - 페이징")
    public void getListByPage() throws Exception {

        // given
        String cmd = """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        App app = new App(br);
        app.run();
        String result = output.toString().trim();

        // then
        assertThat(result)
            .contains("== 명언 앱 ==")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("명령) ")
            .contains("명언 : ")
            .contains("작가 : ")
            .contains("번 명언이 등록되었습니다.")
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")
            .contains("----------------------")
            .contains("번호 / 작가 / 명언")
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = wiseSayingService.getListByKeyword("content", "과거", 1);
        if (!listOfWiseSaying.isEmpty()) {
            listOfWiseSaying.sort((ws1, ws2) -> Integer.compare(ws2.getId(), ws1.getId()));
            for (WiseSaying wiseSaying : listOfWiseSaying) {
                assertThat(result).contains(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
            }
        }

        assertThat(result)
            .contains("명령) ")
            .contains("앱이 종료 되었습니다.");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);
    }



}