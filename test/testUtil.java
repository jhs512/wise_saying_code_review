import Controller.Controller;

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
    public static Scanner[] genScannerArr(final String[] input) {
        final InputStream[] in = new ByteArrayInputStream[input.length];
        Scanner[] s = new Scanner[input.length];
        for(int i = 0; i< input.length; i++){
            in[i] =  new ByteArrayInputStream(input[i].getBytes());
            s[i] = new Scanner(in[i]);
        }

        return s;
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
    public static void input(String str) {
        int chk = 0;
        Controller c = new Controller();
        for (String[] i : StringtoStringArr(str)) {
            chk = c.CheckInputContentTest(testUtil.genScannerArr(i));
            ByteArrayOutputStream output = testUtil.setOutToByteArray();
            String capturedOutput = output.toString();
            System.out.println("Captured: " + capturedOutput);
            testUtil.clearSetOutToByteArray(output);
        }

    }
}