import com.llwiseSaying.App;
import com.llwiseSaying.Util.ConvertData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.llwiseSaying.Repository.WiseSayingRepository.DBdirectoryPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class workspace {

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


}
