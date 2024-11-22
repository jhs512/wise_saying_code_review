import java.io.*;
import java.util.Scanner;

public class TestUtil {
    // 입력 스트림 생성  || 입력 데이터를 프로그램으로 가져오는 흐름
    public static Scanner genScanner(final String input) {
        final InputStream in = new ByteArrayInputStream(input.getBytes()); // 입력 데이터를 ByteArrayInputStream으로 변환
        return new Scanner(in); // 스캐너 객체 생성 (입력을 처리하기 위함)
    }

    // 출력 스트림 설정  || 출력 데이터를 프로그램으로 가져오는 흐름
    public static ByteArrayOutputStream setOutToByteArray() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream(); // 출력 데이터를 저장할 ByteArrayOutputStream 생성
        System.setOut(new PrintStream(output)); // System.out을 ByteArrayOutputStream으로 리다이렉트
        return output; // 리다이렉트된 출력 스트림 반환
    }

    // 출력 스트림 복원
    public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out))); // System.out을 원래 출력 스트림으로 복원
        try {
            output.close(); // 출력 스트림 닫기
        } catch (IOException e) {
            throw new RuntimeException(e); // 닫기 실패 시 예외 발생
        }
    }
}