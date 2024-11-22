package org.example;

import org.example.config.Config;
import org.example.config.TestConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.example.util.TestUtil.*;

class AppTest {
    static public final Config config = new TestConfig();
    static public final App appTest = new App(config);

    static public void clear() {
        appTest.clear();

    }
    static public String run(String input) { // 등록 현재를 사랑하라 작자미상
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outputStream = setOutToByteArray();

        appTest.run();

        clearSetOutToByteArray(outputStream);
        String output = outputStream.toString();
        System.out.println(output);

        return output;
    }
}