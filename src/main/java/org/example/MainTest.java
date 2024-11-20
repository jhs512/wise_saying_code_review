package org.example;

import org.example.config.Config;
import org.example.config.TestConfig;


// 테스트 용이성을 위해 run과 Config의 의존도는 없어져야한다.

public class MainTest {
    public static void main(String[] args) {
        Config config = new TestConfig();
        AppTest testApp = new AppTest(config);
        testApp.integrationTest();
    }
}
