import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    public void t3() throws IOException, ParseException {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    public void testProcessInputAndPrint() {
        // 가짜 입력 설정
        String fakeInput = "JUnit5 is awesome!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(inputStream);

        // 출력 캡처 설정
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out; // 기존 출력 스트림 백업
        System.setOut(printStream);

        // 테스트 실행
        InputProcessor inputProcessor = new InputProcessor();
        inputProcessor.processInputAndPrint();

        // 검증: AssertJ를 사용한 출력 내용 검증
        String output = outputStream.toString();
        assertThat(output)
                .contains("Enter your input:")
                .contains("You entered: JUnit5 is awesome!");

        // System.in과 System.out 복원
        System.setIn(System.in);
        System.setOut(originalOut);
    }
}
