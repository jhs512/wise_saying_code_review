package com.ll.wiseSaying;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WiseControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록 기능 테스트")
    void t1() {
        String rs = AppTest.run("등록\n현재를 사랑하라.\n작자미상\n종료\n");

        assertThat(rs)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록 기능 테스트")
    void t2() {
        String rs = AppTest.run("등록\n현재를 사랑하라.\n작자미상\n목록\n종료\n");

        assertThat(rs)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제 기능 테스트")
    void t3() {
        String rs = AppTest.run("등록\n현재를 사랑하라.\n작자미상\n삭제?id=1\n종료\n");

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("수정 기능 테스트")
    void t4() {
        String rs = AppTest.run("등록\n현재를 사랑하라.\n작자미상\n수정?id=1\n과거를 사랑하라.\n홍길동\n종료\n");

        assertThat(rs)
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("작가(기존) : 작자미상")
                .contains("1번 명언이 수정되었습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 명언 삭제에 대한 예외처리 테스트")
    void t5() {
        String rs = AppTest.run("삭제?id=1\n종료\n");

        assertThat(rs)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 명언 수정에 대한 예외처리 테스트")
    void t6() {
        String rs = AppTest.run("수정?id=1\n종료\n");

        assertThat(rs)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("명언 내용으로 검색")
    void t7() {
        String rs = AppTest.run("""
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            홍길동
            목록?keywordType=content&keyword=현재
            종료
            """);

        assertThat(rs)
                .contains("검색타입 : content")
                .contains("검색어 : 현재")
                .contains("번호 / 작가 / 명언")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .doesNotContain("과거에 집착하지 마라");
    }

    @Test
    @DisplayName("작가로 검색")
    void t8() {
        String rs = AppTest.run("""
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            홍길동
            목록?keywordType=author&keyword=홍길동
            종료
            """);

        assertThat(rs)
                .contains("검색타입 : author")
                .contains("검색어 : 홍길동")
                .contains("번호 / 작가 / 명언")
                .contains("2 / 홍길동 / 과거에 집착하지 마라.")
                .doesNotContain("작자미상");
    }

    @Test
    @DisplayName("검색 결과가 없는 경우")
    void t9() {
        String rs = AppTest.run("""
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            홍길동
            목록?keywordType=content&keyword=미래
            종료
            """);

        assertThat(rs)
                .contains("검색타입 : content")
                .contains("검색어 : 미래")
                .contains("번호 / 작가 / 명언")
                .doesNotContain("현재를 사랑하라")
                .doesNotContain("과거에 집착하지 마라");
    }
}