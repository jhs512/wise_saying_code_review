import Controller.Controller;
import Service.Service;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

//등록 종료 목록 삭제 수정 빌드
public class testCode {
    @Test
    public void testInsert() {
        String input = """
                등록 1 1
                등록 2 2
                등록 3 3
                등록 4 4
                등록 5 5 
                등록 6 6
                등록 7 7
                종료
                """;
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = testUtil.setOutToByteArray();
        Service service = new Service();
        Controller c = new Controller(service, testUtil.genScanner(input));
        c.run();
        testUtil.clearSetOutToByteArray(output);
        String result = output.toString();
        System.out.println(result);
    }

    //keyword keywordType
    @Test
    public void testList() {
        String input = """
                등록 1a 1
                등록 2a 2
                등록 3a 3
                등록 4a 4
                등록 5 5 
                등록 6a 6
                등록 7a 7
                목록
                목록?page=2
                목록?keyword=a&keywordType=author
                목록?keyword=a&keywordType=author&page=2
                종료
                """;
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = testUtil.setOutToByteArray();
        Service service = new Service();
        Controller c = new Controller(service, testUtil.genScanner(input));
        c.run();
        testUtil.clearSetOutToByteArray(output);
        String result = output.toString();
        System.out.println(result);
    }
    @Test
    public void testDelete() {
        String input = """
                등록 1a 1
                등록 2a 2
                등록 3a 3
                등록 4a 4
                등록 5 5 
                등록 6a 6
                등록 7a 7
                삭제?id=2
                삭제?id=10
                삭제?id=2
                삭제?id=-1
                삭제?id=asd
                목록
                목록?page=2
                종료
                """;
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = testUtil.setOutToByteArray();
        Service service = new Service();
        Controller c = new Controller(service, testUtil.genScanner(input));
        c.run();
        testUtil.clearSetOutToByteArray(output);
        String result = output.toString();
        System.out.println(result);
    }
    @Test
    public void testChange() {
        String input = """
                등록 1a 1
                등록 2a 2
                등록 3a 3
                등록 4a 4
                등록 5 5 
                등록 6a 6
                등록 7a 7
                수정?id=2 2b 2
                수정?id=999 2b 2
                수정?id=-1 2b 2
                삭제?id=2
                수정?id=2 2c 2
                목록
                목록?page=2
                종료
                """;
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = testUtil.setOutToByteArray();
        Service service = new Service();
        Controller c = new Controller(service, testUtil.genScanner(input));
        c.run();
        testUtil.clearSetOutToByteArray(output);
        String result = output.toString();
        System.out.println(result);
    }
    @Test
    public void testBuild() {
        String input = """
                등록 1a 1
                등록 2a 2
                등록 3a 3
                등록 4a 4
                등록 5 5 
                등록 6a 6
                등록 7a 7
                등록 1a 1
                등록 2a 2
                등록 3a 3
                등록 4a 4
                등록 5 5 
                등록 6a 6
                등록 7a 7
                빌드
                종료
                """;
        input = input.replace(" ","\n");
        ByteArrayOutputStream output = testUtil.setOutToByteArray();
        Service service = new Service();
        Controller c = new Controller(service, testUtil.genScanner(input));
        c.run();
        testUtil.clearSetOutToByteArray(output);
        String result = output.toString();
        System.out.println(result);
    }
}


