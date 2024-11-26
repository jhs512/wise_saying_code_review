package org.example.mvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WiseSayingControllerTest {
    @Test
    @DisplayName("종료 테스트")
    public void t1() {
        String output = AppTest.run("""
                등록
                작가1
                명언1
                종료
                """);

        assertThat(output).contains("종료되었습니다.");
        System.out.println(output);
    }

    @Test
    @DisplayName("등록 테스트")
    public void t2_1() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                """);

        System.out.println(output);

        assertThat(output)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("5개 이상 등록 테스트")
    public void t2_2() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                등록
                명언3
                작가3
                등록
                명언4
                작가4
                등록
                명언5
                작가5
                등록
                명언6
                작가6
                """);

        System.out.println(output);

        assertThat(output)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .contains("4번 명언이 등록되었습니다.")
                .contains("5번 명언이 등록되었습니다.")
                .contains("6번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("삭제 성공 테스트")
    public void t3_1() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                삭제?id=1
                """);

        System.out.println(output);

        assertThat(output)
                .contains("명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("삭제 실패 테스트")
    public void t3_2() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                삭제?id=10
                """);

        System.out.println(output);

        assertThat(output)
                .contains("명언은 존재하지 않습니다.");
    }



    @Test
    @DisplayName("수정 성공 테스트")
    public void t4_1() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                수정?id=1
                명언1-1
                작가1-1
                """);

        System.out.println(output);

        assertThat(output)
                .contains("명언 수정 완료");
    }

    @Test
    @DisplayName("수정 실패 테스트")
    public void t4_2() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                수정?id=10
                명언1-1
                작가1-1
                """);

        System.out.println(output);

        assertThat(output)
                .contains("명언은 존재하지 않습니다.");
    }


    @Test
    @DisplayName("전체 목록 검색 테스트")
    public void t5_1() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                목록
                """);

        System.out.println(output);

        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("-------------------")
                .contains("1 / 작가1 / 명언1")
                .contains("2 / 작가2 / 명언2");
    }


    @Test
    @DisplayName("작가 검색 테스트")
    public void t5_2() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작자미상2
                목록?keywordType=author&keyword=작자
                """);

        System.out.println(output);

        assertThat(output)
                .contains("2 / 작자미상2 / 명언2")
                .doesNotContain("1 / 작가1 / 명언1");
    }

    @Test
    @DisplayName("페이지 검색 테스트")
    public void t5_3() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                등록
                명언2
                작가2
                등록
                명언3
                작가3
                등록
                명언4
                작가4
                등록
                명언5
                작가5
                등록
                명언6
                작가6
                목록?page=2
                """);

        System.out.println(output);

        assertThat(output)
                .contains("1 / 작가1 / 명언1")
                .contains("페이지 : 1 / [2]");
    }

    @Test
    @DisplayName("빌드 테스트")
    public void t6() {
        String output = AppTest.run("""
                등록
                명언1
                작가1
                빌드
                """);

        System.out.println(output);

        assertThat(output)
                .contains("빌드 완료");
    }

    @Test
    @DisplayName("전체삭제테스트")
    public void t7() {
        String output = AppTest.run("""
                전체삭제
                """);

        assertThat(output).contains("전체 삭제 완료");
    }
}