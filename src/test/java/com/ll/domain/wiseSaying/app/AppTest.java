package com.ll.domain.wiseSaying.app;

import com.ll.util.TestUtil;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTest {
    public static String run(String input) {
        input = input.stripIndent().trim() + "\n종료";
        Scanner scanner = TestUtil.genScanner(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        WiseSayingApp app = new WiseSayingApp(scanner);
        app.run();

        String output = outputStream.toString();

        TestUtil.clearSetOutToByteArray(outputStream);

        scanner.close();

        return output;
    }
}
