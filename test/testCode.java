import Controller.Controller;
import Service.Service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Assertions.*;

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
        String str = testUtil.Run(input);
        assert str.contains("명언 :");
        assert str.contains("작가 :");
        for(int i = 1; i<8; i++)
            assert str.contains(i+"번 명언 등록");


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
        String str = testUtil.Run(input);
        assert str.contains("번호 / 작가 / 명언");
        assert str.contains("7 / 7a / 7");
        assert str.contains("1 / 1a / 1");
        assert str.contains("페이지 : [2] / 2");
        assert str.contains("페이지 : [1] / 2");


    }
    @Test
    public void testDelete() {
        String input = """
                등록 1a 1
                등록 2a 2
                삭제?id=2
                삭제?id=10
                삭제?id=2
                삭제?id=-1
                삭제?id=asd
                종료
                """;
        String str = testUtil.Run(input);
        assert str.contains("삭제완료");
        assert str.contains("10번은 존재 하지않음");
        assert str.contains("2번은 존재 하지않음");
        assert str.contains("-1번은 존재 하지않음");

    }
    @Test
    public void testChange() {
        String input = """
                등록 1a 1
                등록 2a 2
                수정?id=2 2b 2
                수정?id=999 2b 2
                수정?id=-1 2b 2
                삭제?id=2
                수정?id=2 2c 2
                종료
                """;
        String str = testUtil.Run(input);
        assert str.contains("명언(기존) : 삭제된 명언입니다.");
        assert str.contains("명언 : 2c");
        assert str.contains("명언 : 2b");
        assert str.contains("작가 : 2");
        assert str.contains("999번은 존재 하지않음");
        assert str.contains("-1번은 존재 하지않음");

    }
    @Test
    public void testBuild() {
        String input = """
                등록 1a 1
                등록 2a 2
                등록 3a 3
                빌드
                종료
                """;
        String str = testUtil.Run(input);
        assert str.contains("빌드완료");
    }
    @Test
    public void testLoad() {
        String input = """
                로드
                목록
                종료
                """;
        String str = testUtil.Run(input);
        assert str.contains("번호 / 작가 / 명언");
        assert str.contains("3 / 3a / 3");
        assert str.contains("2 / 2a / 2");
        assert str.contains("1 / 1a / 1");
        assert str.contains("정상 로드");
    }
}


