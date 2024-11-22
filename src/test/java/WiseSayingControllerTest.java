import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    public void t1() throws IOException, ParseException {
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
    @DisplayName("목록")
    public void t2() throws IOException, ParseException {
        final String out = AppTest.run("""
                목록
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    public void t3() throws IOException, ParseException {
        final String out = AppTest.run("""
                삭제?id=1
                삭제?id=1
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    public void t4() throws IOException, ParseException {
        final String out = AppTest.run("""
                수정?id=1
                미래를 봐라
                홍길동
                목록
                """);

        assertThat(out)
                .contains("명언(기존) :")
                .contains("명언 :")
                .contains("작가(기존) : ")
                .contains("작가 : ")
                .contains("번호 / 작가 / 명언");
    }

    @Test
    @DisplayName("빌드")
    public void t5() throws IOException, ParseException {
        final String out = AppTest.run("""
                빌드
                """);

        assertThat(out)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }
}
