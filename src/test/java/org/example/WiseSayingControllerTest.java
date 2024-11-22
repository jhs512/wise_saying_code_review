package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WiseSayingControllerTest {

    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("add")
    void addWiseSaying() {
        //given
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
    @DisplayName("get")
    void getWiseSayingList() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                목록
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("delete")
    void deleteWiseSaying() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                삭제?id=1
                삭제?id=2
                종료
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.")
                .contains("2번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("update")
    void updateWiseSaying() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=2
                수정?id=1
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("작가(기존) : 작자미상")
                .contains("1 / 작자미상 / 과거에 집착하지 마라.");
    }
}