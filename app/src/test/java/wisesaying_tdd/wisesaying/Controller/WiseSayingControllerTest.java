package wisesaying_tdd.wisesaying.Controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wisesaying_tdd.AppTest;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    String output;
    
    @Test
    @DisplayName("모드 선택")
    public void SelectMode() {
        output = AppTest.run("""
                개발
                """);
        assertThat(output)
                .contains("개발 모드로 진행합니다. ");
    }

    @Test
    @DisplayName("등록")
    public void DisplayWiseSaying() {
        output = AppTest.run("""
                test
                등록
                명언 1
                작가 1
                """);
        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("등록 완료")
    public void AddSuccess() {
        output = AppTest.run("""
                개발
                등록
                명언 1
                작가 1
                """);
        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("4번 명언이 등록되었습니다. ");
    }

    @Test
    @DisplayName("N건 등록 완료")
    public void MultiAddSuccess() {
        output = AppTest.run("""
                등록
                명언 1
                작가 1
                등록
                명언 2
                작가 2
                등록
                명언 3
                작가 3
                """);
        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("1번 명언이 등록되었습니다. ");
        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("2번 명언이 등록되었습니다. ");
        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("3번 명언이 등록되었습니다. ");
    }

    @Test
    @DisplayName("명언 등록 >> 파일출력")
    public void wisesayingFile() {
        output = AppTest.run("""
                        등록
                        명언 1
                        작가 1
                        """);
        assertThat(output)
                .contains("1번 명언이 등록되었습니다. ");
    }

    @Test
    @DisplayName("목록 헤더 출력")
    public void DisplayListHeader() {
        output = AppTest.run("""
                        목록
                        """);
        assertThat(output).contains("번호 / 작가 / 명언")
                          .contains("----------------------");

    }

    @Test
    @DisplayName("목록 리스트 출력")
    public void DiplayListShow() {
        output = AppTest.run("""
                        목록
                        """);
        assertThat(output).contains("3 / 작가3 / 명언3")
                          .contains("2 / 작가2 / 명언2")
                          .contains("1 / 작가1 / 명언1");
    }
}
