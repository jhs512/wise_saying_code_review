package com.ll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {
    @Test
    @DisplayName("통괄")
    void appTest(){
        App app = new App();

        String in = """
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                빌드
                종료
                """;

        // 원래의 output 스트림을 저장
        PrintStream originalOut = System.out;
        // 새로운 output을 저장할 스트림 생성
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // output을 새로 생성한 스트림에 캡처 (설정?)
        System.setOut(new PrintStream(outStream));

        InputStream out = new ByteArrayInputStream(in.getBytes());
        System.setIn(out);

        app.run();

        assertThat(outStream.toString())
                .contains("== 명언 앱 ==")
                .contains("명령) ")
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명령) ")
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("2번 명언이 등록되었습니다.")
                .contains("명령) ")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("명령) ")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("명령) ")
                .contains("1번 명언은 존재하지 않습니다.")
                .contains("명령) ")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("명언 : ")
                .contains("작가(기존) : 작자미상")
                .contains("작가 : ")
                .contains("명령) ")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
                .contains("명령) ")
                .contains("data.json 파일의 내용이 갱신되었습니다.")
                .contains("명령) ");
    }

}

//import static com.ll.TestUtil.genScanner;
//import static com.ll.TestUtil.setOutToByteArray;

// 38 line
//        // 이부분을  어떻게 처리해야할지 모르겠습니다.
//        // App에서 Controller를 불러 scanner를 사용하는데
//        // 이럴땐 스캐너를 어떻게 넘겨줘야 하는지 모르겠습니다.
//        genScanner(in);
//
//        ByteArrayOutputStream output = setOutToByteArray();
//        app.run();
//        String out = output.toString();