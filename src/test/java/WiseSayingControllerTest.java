
import com.llwiseSaying.App;
import static com.llwiseSaying.Repository.WiseSayingRepository.DBdirectoryPath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;





public class WiseSayingControllerTest {

    ByteArrayOutputStream output;

    @BeforeEach
    void beforeInit(){
        output = AppUtil.setOutToByteArray();
        DBdirectoryPath="db/test/wiseSaying";
    }

    @AfterEach()
    void afterInit() {
        AppUtil.clearSetOutToByteArray(output);
        DBdirectoryPath="db/wiseSaying";
    }

    @Test
    @DisplayName("등록 테스트")
    void testRegistration() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나는 전설이다
                작자미상
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();

        assertThat(capturedOutput)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");


    }



    @Test
    @DisplayName("목록 테스트")
    void testView() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                목록
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.");

    }

    @Test
    @DisplayName("검색 테스트1")
    void testSearch1() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                등록
                안녕하세요.
                친구
                목록?keywordType=content&keyword=안녕하세요
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 친구 / 안녕하세요.");


    }

    @Test
    @DisplayName("검색 테스트2")
    void testSearch2() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                등록
                안녕하세요.
                친구
                목록?keywordType=author&keyword=친구
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 친구 / 안녕하세요.");


    }

    @Test
    @DisplayName("페이징 테스트2")
    void testPaging() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                등록
                안녕하세요.
                친구
                목록?page=1
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("페이지 : 1 / [1]");


    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("1번 명언이 삭제되었습니다");

    }

    @Test
    @DisplayName("삭제 예외처리 테스트")
    void testDeleteVaildation() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=4
                삭제!!id=1
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("4번 명언은 존재하지 않습니다.")
                .contains("명령어 형식을 맞춰주세요.");

    }

    @Test
    @DisplayName("명언 수정 테스트")
    void testModify() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=1
                안녕 안녕
                안녕로봇
                목록
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("작가(기존) : 작자미상")
                .contains("1번 명언이 수정되었습니다")
                .contains("1 / 안녕로봇 / 안녕 안녕");
    }

    @Test
    @DisplayName("수정 예외처리 테스트")
    void testModifyVaildation() throws IOException {
        String simulatedInput="""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=4
                수정!!id=1
                초기화
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();


        assertThat(capturedOutput)
                .contains("4번 명언은 존재하지 않습니다.")
                .contains("명령어 형식을 맞춰주세요.");

    }

    @Test
    @DisplayName("종료 테스트")
    void testExit() throws IOException {
        String simulatedInput="""
                종료
                """;

        AppUtil.setSystemIn(simulatedInput);

        App app=new App();
        app.run();
        String capturedOutput = output.toString();

        assertThat(capturedOutput)
                .contains("프로그램 종료");

    }
}
