import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class testUtil {
    // gen == generate 생성하다.
    // 테스트용 스캐너 생성
    public static Scanner genScanner(final String input) {
        final InputStream in = new ByteArrayInputStream(input.getBytes());

        return new Scanner(in);
    }

    // System.out의 출력을 스트림으로 받기
    public static ByteArrayOutputStream setOutToByteArray() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        return output;
    }

    // setOutToByteArray 함수의 사용을 완료한 후 정리하는 함수, 출력을 다시 정상화 하는 함수
    public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String[]> StringtoStringArr(String str){
        ArrayList<String[]> arr = new ArrayList<>();
        for(String i: str.split("\n"))
                arr.add(i.split(" "));

        return arr;
    }
    public static String Run(String input){
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = setOutToByteArray();
        WiseSayingService service = new WiseSayingService();
        WiseSayingController c = new WiseSayingController(service, genScanner(input));
        c.run();
        clearSetOutToByteArray(output);
        System.out.println(output.toString());
        return output.toString();
    }

}