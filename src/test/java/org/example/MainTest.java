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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {

    @AfterEach
    public void cleanUp() throws IOException {
        // 디렉토리 삭제 전에 파일이나 디렉토리 내용이 있다면 삭제
        File directory = new File(ConfigReader.getProperty("test.save.path"));
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
    @DisplayName("입력 출력 테스트 1")
    public void inputAndOutputTest1() throws Exception {

        // given
        String cmd = "종료";
        BufferedReader br = TestUtil.genBufferedReader(cmd);

        // 출력 스트림 캡처
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();  // 출력 캡처 준비

        // when
        // 입력값 읽기 및 출력
        String input = br.readLine();
        System.out.println(input);

        // then
        String result = output.toString().trim();  // 출력된 내용 가져오기
//        System.err.println(result);  // 실제 출력된 내용 확인

        // 종료 명령어 입력 후 출력 결과 확인
        assertThat(result).isEqualTo("종료");

        // cleanup
        TestUtil.clearSetOutToByteArray(output);  // System.out 복원
    }

    @Test
    @DisplayName("입력 출력 테스트 2")
    public void inputAndOutputTest2() throws Exception {

        // given
        String cmd = """
            목록
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        String input;
        while((input = br.readLine()) != null) {
            System.out.println(input);
        }
        String result = output.toString().trim();

        // then
        assertThat(result).contains("목록").contains("종료");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    @DisplayName("입력 출력 테스트 3")
    public void inputAndOutputTest3() throws Exception {

        // given
        String cmd = """
            목록
            종료
            """;
        BufferedReader br = TestUtil.genBufferedReader(cmd);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        // when
        String input;
        String[] inputs = {"목록", "종료"};
        int idx = 0;
        while((input = br.readLine()) != null) {
            System.out.println(input);
            assertThat(input).isEqualTo(inputs[idx++]);
        }

        String result = output.toString().trim();
        System.out.println(result);

        // then
        assertThat(result).contains("목록").contains("종료");

        TestUtil.clearSetOutToByteArray(output);
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
    @DisplayName("명언 목록 테스트")
    public void getAllWiseSayingTest() throws Exception {

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
            .contains("--------------------");

        List<WiseSaying> listOfWiseSaying = WiseSayingService.getListOfWiseSaying();
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



}