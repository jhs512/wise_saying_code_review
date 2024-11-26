package wisesaying_tdd;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wisesaying_tdd.wisesaying.esset.TestUtil;

public class TrstUtilTest {

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
    
}
