import org.example.controller.WiseSayingController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppTest {
    private static final String TEST_JSON_FILE = "src/test/java/resources/test_data.json";
    private static final String TEST_LAST_ID_FILE = "src/test/java/resources/test_lastId.txt";

    private Scanner scanner;
    ByteArrayOutputStream outputStream;
    static List<String> testCommandList = new ArrayList<>();

    @BeforeAll
    static void setUpAll(){
        testCommandList.add("""
                wrong command
                종료
                """);
        testCommandList.add("""
                등록
                테스트명언1
                테스트작가1
                종료
                """);
        testCommandList.add("""
                등록
                테스트명언1
                테스트작가1
                수정?id=1
                테스트명언(수정)
                목록
                종료
                """);
        testCommandList.add("""
                등록
                테스트명언1
                테스트작가1
                삭제?id=1
                종료
                """);
    }

    @BeforeEach
    void setUp(){
        File testJsonFile = new File(TEST_JSON_FILE);
        File testLastIdFile = new File(TEST_LAST_ID_FILE);
        try(
                FileWriter testJsonWriter = new FileWriter(testJsonFile);
                FileWriter testLastIdWriter = new FileWriter(testLastIdFile)
        ){
            testJsonWriter.write("");
            testLastIdWriter.write("");
        }catch (Exception ignore){
        }
        outputStream = TestUtil.setOutToByteArray();
    }

    @AfterEach
    void tearDown(){
        TestUtil.clearSetOutToByteArray(outputStream);
        scanner.close();
    }


    private void runTest(int order){
        scanner = TestUtil.genScanner(testCommandList.get(order));
        WiseSayingController wiseSayingController = new WiseSayingController(scanner, TEST_JSON_FILE, TEST_LAST_ID_FILE);
        wiseSayingController.run();
    }

    @Test
    void testWrongCommand() {
        runTest(0);
        String result = outputStream.toString();
        assertTrue(result.contains("== 잘못된 명령어입니다. =="));
    }

    @Test
    void testRegister() {
        runTest(1);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 등록되었습니다."));
    }

    @Test
    void testModify(){
        runTest(2);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 등록되었습니다."));
        assertTrue(result.contains("테스트명언(수정)"));
    }

    @Test
    void testDelete(){
        runTest(3);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 삭제되었습니다."));
    }
}
