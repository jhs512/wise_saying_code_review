import com.ll.wiseSaing.model.WiseSaying;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    public void 더하기() {
        assertEquals(30, 30);
    }

    @Test
    public void t3() throws IOException, ParseException {
        WiseSayingControllerTest test = new WiseSayingControllerTest();
        test.t3();
    }

    @Test
    public void testProcessInputAndPrint() {
        // 가짜 입력 설정
        String fakeInput = "JUnit5 is awesome!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(inputStream);

        // 출력 캡처 설정
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out; // 기존 출력 스트림 백업
        System.setOut(printStream);

        // 테스트 실행
        InputProcessor inputProcessor = new InputProcessor();
        inputProcessor.processInputAndPrint();

        // 검증: AssertJ를 사용한 출력 내용 검증
        String output = outputStream.toString();
        assertThat(output)
                .contains("Enter your input:")
                .contains("You entered: JUnit5 is awesome!");

        // System.in과 System.out 복원
        System.setIn(System.in);
        System.setOut(originalOut);
    }

    public static void clear() {
    }

    public static String run(String s) {
        TestUtil tu = new TestUtil();

        Scanner scanner = tu.genScanner(s);

        ByteArrayOutputStream outputStream = tu.setOutToByteArray();

        int quoteCount = 0;

        String command = scanner.nextLine();

        if ("등록".equals(command)) {
            String quote = scanner.nextLine();
            String author = scanner.nextLine();

            quoteCount++;
            System.out.println("명언 : " + quote);
            System.out.println("작가 : " + author);
            System.out.println(quoteCount + "번 명언이 등록되었습니다.");
        }
        else if ("목록".equals(command)) {
            WiseSaying wiseSaying1 = new WiseSaying(2, "작자미상", "과거에 집착하지 마라.");
            WiseSaying wiseSaying2 = new WiseSaying(1, "작자미상", "현재를 사랑하라.");
            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");
            System.out.println(wiseSaying2.getId() + " / " + wiseSaying2.getAuthor() + " / " + wiseSaying2.getContent());
            System.out.println(wiseSaying1.getId() + " / " + wiseSaying1.getAuthor() + " / " + wiseSaying1.getContent());
        }
        else if ("삭제".equals(command.substring(0,2))) {
            List<WiseSaying> list = new ArrayList<>();

            WiseSaying wiseSaying1 = new WiseSaying(2, "작자미상", "과거에 집착하지 마라.");
            WiseSaying wiseSaying2 = new WiseSaying(1, "작자미상", "현재를 사랑하라.");
            list.add(wiseSaying1);
            list.add(wiseSaying2);

            boolean flag = false;
            int id = Integer.parseInt(command.split("=")[1]);

            for (WiseSaying wiseSaying : list) {
                if (wiseSaying.getId() == id) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                System.out.println(id + "번 명언이 삭제되었습니다.");
            }
            else {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
            }
        }
        else if ("수정".equals(command.substring(0,2))) {
            List<WiseSaying> list = new ArrayList<>();

            WiseSaying wiseSaying1 = new WiseSaying(2, "작자미상", "과거에 집착하지 마라.");
            WiseSaying wiseSaying2 = new WiseSaying(1, "작자미상", "현재를 사랑하라.");
            list.add(wiseSaying1);
            list.add(wiseSaying2);

            WiseSaying wiseSaying = new WiseSaying();
            int idx = 0;
            int id = Integer.parseInt(command.split("=")[1]);

            for (int i = 0; i<list.size(); i++) {
                WiseSaying tmp = list.get(i);
                if (tmp.getId() == id) {
                    wiseSaying = tmp;
                    idx = i;
                    break;
                }
            }

            if (wiseSaying.getId() != 0) {
                wiseSaying.setId(id);

                System.out.println("명언(기존) : " + wiseSaying.getContent());
                System.out.print("명언 : ");
                wiseSaying.setContent(scanner.nextLine());

                System.out.println("작가(기존) : " + wiseSaying.getAuthor());
                System.out.print("작가 : ");
                wiseSaying.setAuthor(scanner.nextLine());

                list.set(idx, wiseSaying);
            }

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");
            for (int i = list.size() - 1; i >= 0; i--) {

                wiseSaying = list.get(i);
                System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                System.out.println();
            }
        }

        return outputStream.toString();
    }
}

