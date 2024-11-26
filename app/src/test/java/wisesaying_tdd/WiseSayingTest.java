package wisesaying_tdd;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class WiseSayingTest {
    
    

    @Test
    private static void Test1() {
        Scanner sc = new Scanner(System.in,"EUC-KR");
        String a = sc.nextLine(); 
    }

    @Test
    private static void Test2() {
        InputStream in = new ByteArrayInputStream("list".getBytes());
        Scanner sc2 = new Scanner(in);
        String a = sc2.nextLine();
        assertThat(a).isEqualTo("list");
        
    }
}
