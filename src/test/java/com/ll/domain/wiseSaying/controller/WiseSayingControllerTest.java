package com.ll.domain.wiseSaying.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ll.domain.wiseSaying.app.AppTest;
import com.ll.domain.wiseSaying.config.AppConfig;
import com.ll.global.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WiseSayingControllerTest {
    @BeforeAll
    public static void beforeAll() {
        AppConfig.setTestMode();
    }

    @AfterEach
    public void afterEach() {
        Util.File.delete(AppConfig.getTestDir());
    }

    @Test
    @DisplayName("== 명언 앱 ==")
    void printBasic() {
        String output = AppTest.run("");

        assertThat(output)
                .contains("== 명언 앱 ==")
                .contains("명령) ");
    }

    @Test
    @DisplayName("명령) ")
    void printContinue() {
        String output = AppTest.run("""
                목록
                목록
                """);

        String[] split = output.split("명령\\)");
        assertThat(split).hasSize(4);
    }

    @Test
    @DisplayName("등록")
    void regist() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(output)
                .contains("명언 : ")
                .contains("작가 : ");
    }

    @Test
    @DisplayName("id 증가 확인")
    void increaseId() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(output)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.")
                .contains("3번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void getWiseSayings() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                목록
                """);

        assertThat(output)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("3 / 이순신 / 나의 죽음을 적들에게 알리지 말라!")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제")
    public void delete() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                목록
                """);

        assertThat(output)
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("삭제 실패")
    public void deleteFail() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=3
                목록
                """);

        assertThat(output)
                .contains("3번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    public void modify() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=2
                새 명언 내용
                새 작가
                """);

        assertThat(output)
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상");
    }

    @Test
    @DisplayName("수정 성공")
    public void modifySuccess() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                """);

        assertThat(output)
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    @Test
    @DisplayName("빌드")
    public void build() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                빌드
                """);

        assertThat(output)
                .contains("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Test
    @DisplayName("목록(검색)")
    public void search() {
        String output = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                """).stripIndent().trim();

        assertThat(output)
                .contains("""
                        ----------------------
                        검색타입 : content
                        검색어 : 과거
                        """.stripIndent().trim());

        assertThat(output)
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    @DisplayName("샘플데이터 생성")
    public void makeSampleData() {
        AppTest.makeSampleData(20);

        assertEquals("20", Util.File.get(AppConfig.getDir() + "/lastId.txt"));
    }

    @Test
    @DisplayName("목록(페이징) : page=1")
    public void page1() {
        AppTest.makeSampleData(10);

        String output = AppTest.run("""
                목록?page=1
                """);

        assertThat(output)
                .contains("10 / 작가10 / 명언10")
                .contains("6 / 작가6 / 명언6")
                .contains("페이지 : [1] / 2")
                .doesNotContain("5 / 작가5 / 명언5")
                .doesNotContain("1 / 작가1 / 명언1");
    }

    @Test
    @DisplayName("목록(페이징) : page=2")
    public void page2() {
        AppTest.makeSampleData(10);

        String output = AppTest.run("""
                목록?page=2
                """);

        assertThat(output)
                .contains("5 / 작가5 / 명언5")
                .contains("1 / 작가1 / 명언1")
                .contains("페이지 : 1 / [2]")
                .doesNotContain("10 / 작가10 / 명언10")
                .doesNotContain("6 / 작가6 / 명언6");
    }

    @Test
    @DisplayName("목록?page&keywordType&keyword")
    public void pageAndSearchForAll() {
        AppTest.makeSampleData(10);

        String output = AppTest.run("""
                목록?page=2&keywordType=content&keyword=명언
                """);

        assertThat(output)
                .contains("5 / 작가5 / 명언5")
                .contains("1 / 작가1 / 명언1")
                .contains("페이지 : 1 / [2]")
                .doesNotContain("10 / 작가10 / 명언10")
                .doesNotContain("6 / 작가6 / 명언6");
    }

    @Test
    @DisplayName("1페이지, keyword=1")
    public void page1Keyword1() {
        AppTest.makeSampleData(10);

        String output = AppTest.run("""
                목록?page=1&keywordType=content&keyword=1
                """);

        assertThat(output)
                .contains("10 / 작가10 / 명언10")
                .contains("1 / 작가1 / 명언1")
                .contains("페이지 : [1]")
                .doesNotContain("9 / 작가9 / 명언9")
                .doesNotContain("2 / 작가2 / 명언2");
    }

    @Test
    @DisplayName("1페이지, keyword=1")
    public void page1OnlyKeyword1() {
        AppTest.makeSampleData(10);

        String output = AppTest.run("""
                목록?page=1&keyword=1
                """);

        assertThat(output)
                .contains("10 / 작가10 / 명언10")
                .contains("1 / 작가1 / 명언1")
                .contains("페이지 : [1]")
                .doesNotContain("9 / 작가9 / 명언9")
                .doesNotContain("2 / 작가2 / 명언2");
    }
}