package wisesaying_tdd.wisesaying.Util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestUtilTest {

    // 테스트 유틸 - 스캐너 생성 테스트 
    @Test
    @DisplayName("스캐너 생성 테스트")
    public void t1() {
        Scanner sc =TestUtil.genScanner("""
                등록
                명언 1
                작가 1
                """.stripIndent().trim());
        String cmd = sc.nextLine();
        String content = sc.nextLine();
        String authur = sc.nextLine();

        assertThat(cmd).isEqualTo("등록");
        assertThat(content).isEqualTo("명언 1");
        assertThat(authur).isEqualTo("작가 1");
    }

    // byteArrayOutputStream 테스트 
    @Test
    @DisplayName("byteArrayOutputStream 생성 테스트 ")
    public void t2() {
        ByteArrayOutputStream byteArrayOutputStream =TestUtil.setOutToByteArray();

        System.out.println("2 / 작가2 / 명언2");
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        String out2 = byteArrayOutputStream.toString().trim();

        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
        }

        assertThat(out2).isEqualTo("2 / 작가2 / 명언2");

    }
    
}
