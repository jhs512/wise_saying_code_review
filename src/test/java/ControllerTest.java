import org.example.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerTest {
    private WiseController controller;
    private WiseService service;
    private WiseRepository repository;
    private ByteArrayOutputStream output;

    @Before
    public void setUp() {
        repository = new WiseRepository("testdb/wiseSaying/");
        service = new WiseServiceImpl(repository);
        output = TestUtil.setOutToByteArray();
    }

    @After
    public void tearDown() {
        TestUtil.clearSetOutToByteArray(output);
        repository.deleteAll();
    }

    @Test
    public void applyWise_success() throws IOException {
        Scanner scanner = TestUtil.genScanner("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");

        assertThat(repository.findWise(1).toString())
                .isEqualTo("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    public void printWise_success() throws IOException {
        Scanner scanner = TestUtil.genScanner("목록");
        addTestCases();

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("번호 / 작가 / 명언")
                .contains("---------------")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    @Test
    public void deleteWise_success() throws IOException {
        Scanner scanner = TestUtil.genScanner("삭제?id=1");
        addTestCase();

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("1번 명언이 삭제되었습니다.");

        assertThat(repository.findWise(1))
                .isEqualTo(null);
    }

    @Test
    public void deleteWise_noWiseError() {
        Scanner scanner = TestUtil.genScanner("삭제?id=1");

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    public void editWise_Success() throws IOException {
        Scanner scanner = TestUtil.genScanner("""
                수정?id=1
                현재와 과거를 사랑하라.
                홍길동
                """);
        addTestCase();

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("명언(기존) : 현재를 사랑하라.")
                .contains("명언 :")
                .contains("작가(기존) : 작자미상")
                .contains("작가 :");

        assertThat(repository.findWise(1).toString())
                .isEqualTo("1 / 홍길동 / 현재와 과거를 사랑하라.");
    }

    @Test
    public void editWise_noWiseError() throws IOException {
        Scanner scanner = TestUtil.genScanner("""
                수정?id=1
                현재와 과거를 사랑하라.
                홍길동
                """);

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    public void buildWise() throws IOException {
        Scanner scanner = TestUtil.genScanner("빌드");
        addTestCases();

        controller = new WiseController(service, scanner);
        controller.handleCommand();

        String result = output.toString();
        assertThat(result)
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        List<Wise> wises = repository.getWises(1);
        assertThat(wises.get(0).toString())
                .isEqualTo("1 / 작자미상 / 현재를 사랑하라.");

        assertThat(wises.get(1).toString())
                .isEqualTo("2 / 작자미상 / 과거에 집착하지 마라.");
    }

    private void addTestCase() throws IOException {
        repository.applyWise("현재를 사랑하라.", "작자미상");
    }

    private void addTestCases() throws IOException {
        repository.applyWise("현재를 사랑하라.", "작자미상");
        repository.applyWise("과거에 집착하지 마라.", "작자미상");
    }
}