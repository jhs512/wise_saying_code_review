package org.example.mvc;

import org.example.App;
import org.example.mvc.testutil.TestUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

public class AppTest {

    public static String run(String input) {
        input = input.stripIndent().trim() + "\n전체삭제\n종료\n";
        BufferedReader bufferedReader = TestUtil.genBufferedReader(input);
        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        App app = new App(bufferedReader);
        app.run();

        String output = outputStream.toString().trim();

        TestUtil.clearSetOutToByteArray(outputStream);

        return output;
    }

}
