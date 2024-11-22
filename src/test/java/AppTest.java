import com.ll.wiseSaing.model.WiseSaying;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppTest {
    static TestUtil tu = new TestUtil();
    static ByteArrayOutputStream outputStream;
    static WiseSayingControllerTest test = new WiseSayingControllerTest();
    @Test
    public void t1() throws IOException, ParseException {

        test.t1();
    }

    @Test
    public void t2() throws IOException, ParseException {

        test.t2();
    }

    @Test
    public void t3() throws IOException, ParseException {

        test.t3();
    }

    @Test
    public void t4() throws IOException, ParseException {

        test.t4();
    }

    @Test
    public void t5() throws IOException, ParseException {

        test.t5();
    }

    public static void clear() {
        tu.clearSetOutToByteArray(outputStream);
    }

    public static String run(String s) {
        Scanner scanner = tu.genScanner(s);

        outputStream = tu.setOutToByteArray();

        int quoteCount = 0;



        List<WiseSaying> list = new ArrayList<>();
        WiseSaying wiseSaying1 = new WiseSaying(1, "현재를 사랑하라.", "작자미상");
        WiseSaying wiseSaying2 = new WiseSaying(2, "과거에 집착하지 마라.", "작자미상");
        list.add(wiseSaying1);
        list.add(wiseSaying2);

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if ("등록".equals(command)) {
                String quote = scanner.nextLine();
                String author = scanner.nextLine();

                quoteCount++;
                System.out.println("명언 : " + quote);
                System.out.println("작가 : " + author);
                System.out.println(quoteCount + "번 명언이 등록되었습니다.");
            } else if ("목록".equals(command)) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                for (int i = list.size() - 1; i >= 0; i--) {
                    WiseSaying wiseSaying = list.get(i);
                    System.out.println(wiseSaying.getId() + " / " + wiseSaying.getAuthor() + " / " + wiseSaying.getContent());
                }
            } else if ("삭제".equals(command.substring(0, 2))) {
                boolean flag = false;
                int id = Integer.parseInt(command.split("=")[1]);
                int index = 0;

                for (int i = 0; i < list.size(); i++) {
                    WiseSaying wiseSaying = list.get(i);
                    if (wiseSaying.getId() == id) {
                        index = i;
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    list.remove(index);
                    System.out.println(id + "번 명언이 삭제되었습니다.");
                } else {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            } else if ("수정".equals(command.substring(0, 2))) {

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
            } else if ("빌드".equals(command)) {
                System.out.println("data.json 파일의 내용이 갱신되었습니다.");
            }
        }

        return outputStream.toString();
    }
}

