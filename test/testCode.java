import Controller.Controller;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

//등록 종류 목록 삭제 수정 빌드
public class testCode {
    @Test
    public void test1() {
        String[][] input = {{"등록", "1치킨", "1"},{"등록", "2", "2피자"},{"등록", "1치킨2", "1"},{"등록", "2", "2피자2"},{"목록"},{"목록?keyword=치킨&keywordType=author"},{"목록?keyword=피자&keywordType=content"},{"삭제?id=5"},{"수정?id=2","수정1","수정1"},{"수정?id=2134","수정1","수정1"},{"빌드"},{"종료"}};
        testUtil.input(input);
    }
    //@Test
    public void test2() {
        String[][] input = {{"등록>>", "1>>", "<<1"},{"!등록", "@2", "#2"},{"목록"},{"삭제?id=5"},{"수정?id=2","수$정1","수정%1"},{"빌드"},{"종료"}};
        testUtil.input(input);
    }
   // @Test
    public void test3() {
        String[][] input = {{"!"},{"@"},{"#"},{"$"},{"%"},{"^"},{"&"},{"*"},{"("},{")"},{"[]"},{"{}"},{"()"},{">>"},{"<<"},{"?"},{"<"},{">"},{"종료"}};
        testUtil.input(input);
    }
    //@Test
    public void test4() {
        String[][] input = {{"등록&등록", "1>>!실행", "1<<1"},{"!등록==등록", "@2", "#2"},{"목록"},{"삭제?id=5"},{"1>수정?id=2","$수정1","수정%1=0"},{"(빌드)"},{"종료"}};
        testUtil.input(input);
    }
}

