import com.ll.QuoteApp;
import com.ll.QuoteController;
import com.ll.QuoteService;
import com.ll.QuoteStore;
import com.ll.utils.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class QuoteControllerTest {

    FileManager fileManager;
    QuoteStore quoteStore;
    QuoteService quoteService;
    QuoteController quoteController;
    QuoteApp quoteApp ;

    public QuoteControllerTest() {
        fileManager = new FileManager("./src/test/java/testDb", ".json");
        quoteStore = new QuoteStore(fileManager);
        quoteService = new QuoteService(quoteStore);
        quoteController = new QuoteController(quoteService);
    }

    @BeforeEach
    void beforeEach() {
        quoteStore.clear();
        quoteApp = null;
    }

    @Test
    public void 등록() {
        String input = """
                등록
                돌다리도 두들겨보고..
                작가미상
                목록
                종료""";

        Scanner scanner = TestUtil.genScanner(input);
        quoteApp = new QuoteApp(quoteController, scanner);

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        quoteApp.run();

        String result = output.toString().trim();
        assertThat(result).contains("0 / 작가미상 / 돌다리도 두들겨보고..");

        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    public void 수정() {
        String input = """
                등록
                돌다리도 두들겨보고..
                작가미상
                수정?id=0
                굿
                나
                목록
                종료""";

        Scanner scanner = TestUtil.genScanner(input);
        quoteApp = new QuoteApp(quoteController, scanner);

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        quoteApp.run();

        String result = output.toString().trim();

        assertThat(result)
                .contains("0번 명언이 수정되었습니다.")
                .contains("0 / 나 / 굿");


        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    public void 삭제() {
        String input = """
                등록
                돌다리도 두들겨보고..
                작가미상
                삭제?id=0
                목록
                종료""";

        Scanner scanner = TestUtil.genScanner(input);
        quoteApp = new QuoteApp(quoteController, scanner);

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        quoteApp.run();

        String result = output.toString().trim();

        assertThat(result)
                .contains("0번 명언이 삭제되었습니다.")
                .doesNotContain("0 / 작가미상 / 돌다리도 두들겨보고..");


        TestUtil.clearSetOutToByteArray(output);
    }


}
