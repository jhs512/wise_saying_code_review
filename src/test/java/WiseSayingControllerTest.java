import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear(); // 테스트 환경 초기화
    }

    @Test
    @DisplayName("등록")
    void t1() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t2() {
        AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        AppTest.run("""
                등록
                과거에 집착하지 마라.
                작자미상
                종료
                """);

        final String out = AppTest.run("""
                목록
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("삭제")
    void t3() {
        AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        final String out = AppTest.run("""
                삭제?id=1
                목록
                종료
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("등록된 명언이 없습니다.");
    }

    @Test
    @DisplayName("수정")
    void t4() {
        AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        final String out = AppTest.run("""
                수정?id=1
                현재와 미래를 사랑하라.
                작자미상
                목록
                종료
                """);

        assertThat(out)
                .contains("1번 명언이 수정되었습니다.")
                .contains("1 / 작자미상 / 현재와 미래를 사랑하라.");
    }
}