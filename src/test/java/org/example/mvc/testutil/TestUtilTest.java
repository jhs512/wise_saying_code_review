package org.example.mvc.testutil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtilTest {

    @Test
    @DisplayName("TestUtil.genBufferedReader()")
    public void t1() throws IOException {
        BufferedReader bufferedReader = TestUtil.genBufferedReader("""
                등록
                나의 죽음을 적에게 알리지 마라
                이순신
                """.stripIndent().trim());

        String cmd = bufferedReader.readLine();
        String content = bufferedReader.readLine();
        String author = bufferedReader.readLine();

        assertThat(cmd).isEqualTo("등록");
        assertThat(content).isEqualTo("나의 죽음을 적에게 알리지 마라");
        assertThat(author).isEqualTo("이순신");
    }

    @Test
    @DisplayName("TestUtil.setOutToByteArray()")
    public void t2() {
        ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

        System.out.println("2 / 이순신 / 나의 죽음을 적에게 알리지 마라");
        String out = byteArrayOutputStream.toString().trim();
        TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

        assertThat(out).isEqualTo("2 / 이순신 / 나의 죽음을 적에게 알리지 마라");
    }
}
