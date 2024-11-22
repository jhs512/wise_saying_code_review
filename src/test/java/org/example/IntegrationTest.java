package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

class IntegrationTest {

    public static class TestUtil {
        // gen == generate 생성하다.
        // 테스트용 스캐너 생성
        public static BufferedReader genBufferReader(final String input) {
            final InputStream in = new ByteArrayInputStream(input.getBytes());

            return new BufferedReader(new InputStreamReader(in));
        }

        // System.out의 출력을 스트림으로 받기
        public static ByteArrayOutputStream setOutToByteArray() {
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(output));

            return output;
        }

        // setOutToByteArray 함수의 사용을 완료한 후 정리하는 함수, 출력을 다시 정상화 하는 함수
        public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            try {
                output.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ByteArrayOutputStream baos = TestUtil.setOutToByteArray();
    private WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    private WiseSayingService wiseSayingService;
    private WiseSayingController wiseSayingController;


    private String registCmd = """
            등록
            테스트명언
            테스트작가
            """;
    private String endCmd = """
            종료
            """;

    String dbDir = ".\\src\\test\\db\\wiseSaying\\";
    private App app;

    public void configAndRumApp(String cmd) {
        BufferedReader tbr = TestUtil.genBufferReader(cmd);
        wiseSayingController = new WiseSayingController(tbr);
        wiseSayingController.setWiseSayingService(wiseSayingService);
        app = new App(tbr, wiseSayingController);
        app.run();
    }

    @BeforeEach
    void BeforeEach() {
        wiseSayingRepository.setDbDir(dbDir);
        wiseSayingService = new WiseSayingService(wiseSayingRepository);
        wiseSayingRepository.setLastId(1);
    }

    @AfterEach
    void AfterEach() {
        File[] testFiles = new File(dbDir).listFiles();
        for (File tFs : testFiles) {
            tFs.delete();
        }
    }

    @DisplayName("명언 등록")
    @Test
    void Regist() {
        String cmd = registCmd + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("명언이 등록되었습니다."));
        assertTrue(new File(dbDir + "1.json").exists());
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("목록 조회")
    @Test
    void ViewAll() {
        String cmd = registCmd + """
                목록
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("테스트명언") && out.contains("테스트작가") && out.contains("페이지 : [1]"));
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("명언 삭제")
    @Test
    void Delete() {
        String cmd = registCmd + """
                삭제?id=1
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("명언이 삭제되었습니다."));
        assertTrue(!new File(dbDir + "1.json").exists());
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("명언 삭제 실패")
    @Test
    void DeleteFailed() {
        String cmd = registCmd + """
                삭제?id=2
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("명언은 존재하지 않습니다."));
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("명언 수정")
    @Test
    void Update() {
        String cmd = registCmd + """
                수정?id=1
                수정명언
                수정작가
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        WiseSaying updatedWS = wiseSayingRepository.findById(1);
        assertEquals(updatedWS.getSaying(), "수정명언");
        assertEquals(updatedWS.getWritter(), "수정작가");
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("data.json 빌드")
    @Test
    void Build() {
        String cmd = registCmd + """
                빌드
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("data.json 파일의 내용이 갱신되었습니다."));
        assertTrue(new File(dbDir + "data.json").exists());
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("명언 검색 type = content")
    @Test
    void SearchWiseSayingBySaying() {
        String cmd = registCmd + """
                목록?type=content&keyword=테스트
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("검색타입 : content") && out.contains("검색어 : 테스트") && out.contains("테스트작가") && out.contains("페이지 : [1]"));
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @DisplayName("명언 검색 type = author")
    @Test
    void SearchWiseSayingByWritter() {
        String cmd = registCmd + """
                목록?type=author&keyword=테스트
                """ + endCmd;
        configAndRumApp(cmd);
        String out = baos.toString();

        assertTrue(out.contains("검색타입 : author") && out.contains("검색어 : 테스트") && out.contains("테스트명언"));
        TestUtil.clearSetOutToByteArray(baos);

        System.out.println(out);
    }

    @Test
    void test() throws IOException {
        String command = "page=2&type=author&keyword=작가";
        configAndRumApp(command);
        TestUtil.clearSetOutToByteArray(baos);
        System.out.println(wiseSayingController.extractParam(command));

    }

}