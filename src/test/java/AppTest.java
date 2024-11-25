import org.example.controller.WiseSayingController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class AppTest {
    private static final String TEST_JSON_FILE = "src/test/java/resources/test_data.json";
    private static final String TEST_LAST_ID_FILE = "src/test/java/resources/test_lastId.txt";

    private static final String WRONG_COMMAND = "잘못된 명령어";
    private static final String REGISTER = "등록";
    private static final String LIST = "목록";
    private static final String MODIFY = "수정";
    private static final String DELETE = "삭제";

    private Scanner scanner;
    ByteArrayOutputStream outputStream;
    static Map<String, String> testCommandMap = new HashMap<>();

    @BeforeAll
    static void setUpAll(){
        testCommandMap.put(WRONG_COMMAND, """
                wrong command
                종료
                """);
        testCommandMap.put(REGISTER,"""
                등록
                테스트명언1
                테스트작가1
                종료
                """);
        testCommandMap.put(LIST,"""
                등록
                테스트명언1
                테스트작가1
                목록
                종료
                """);
        testCommandMap.put(MODIFY,"""
                등록
                테스트명언1
                테스트작가1
                수정?id=1
                테스트명언(수정)
                목록
                종료
                """);
        testCommandMap.put(DELETE,"""
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


    private void runTest(String command){
        scanner = TestUtil.genScanner(testCommandMap.get(command));
        WiseSayingController wiseSayingController = new WiseSayingController(scanner, TEST_JSON_FILE, TEST_LAST_ID_FILE);
        wiseSayingController.run();
    }

    @Test
    void testWrongCommand() {
        runTest(WRONG_COMMAND);
        String result = outputStream.toString();
        assertTrue(result.contains("== 잘못된 명령어입니다. =="));
    }

    @Test
    void testRegister() {
        runTest(REGISTER);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 등록되었습니다."));
    }

    @Test
    void testList() {
        runTest(LIST);
        String result = outputStream.toString();
        assertTrue(result.contains("테스트명언1"));
    }

    @Test
    void testModify(){
        runTest(MODIFY);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 등록되었습니다."));
        assertTrue(result.contains("테스트명언(수정)"));
    }

    @Test
    void testDelete(){
        runTest(DELETE);
        String result = outputStream.toString();
        assertTrue(result.contains("1번 명언이 삭제되었습니다."));
    }
}
