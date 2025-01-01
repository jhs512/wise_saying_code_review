package com.ll.domain.wiseSaying.app;

import com.ll.domain.wiseSaying.config.AppConfig;
import com.ll.domain.wiseSaying.entity.WiseSaying;
import com.ll.domain.wiseSaying.repository.WiseSayingFileRepository;
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

    public static void makeSampleData(int items) {
        WiseSayingFileRepository repository = new WiseSayingFileRepository();
        AppConfig.setTestMode();

        for (int i = 1; i <= items; i++) {
            repository.saveFile(new WiseSaying(0, "작가" + i, "명언" + i));
        }
    }
}
