package org.example.mvc;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    Repository repository = new Repository();
    Service service = new Service(repository);

    String registerSimulationInput = "등록\n명언1\n작가1\n종료\n";

    String deleteSuccessSimulationInput = "등록\n명언1\n작가1\n삭제?id=1\n종료\n";
    String deleteFailSimulationInput = "삭제?id=10\n종료\n";

    String updateSuccessSimulationInput = "등록\n명언1\n작가1\n수정?id=1\n명언1-2\n작가1-2\n종료\n";
    String updateFailSimulationInput = "수정?id=10\n종료\n";

    String listSimulationInput = "등록\n명언1\n작가1\n등록\n명언2\n작가2\n목록\n종료\n";
    String searchSimulationInput = "등록\n명언1\n작가1\n목록?keyword=명언1&keywordType=content\n종료\n";

    String buildSimulationInput = "빌드\n종료\n";

    @Test
    void 등록테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        Controller controller = new Controller(service, TestUtil.genScanner(registerSimulationInput));
        controller.run();

        String result = output.toString().trim();

        int id = repository.getId();

        assertTrue(result.contains(id + "번 명언이 등록되었습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 삭제성공테스트() {

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        String id = deleteSuccessSimulationInput.split("\n")[3].split("=")[1];

        Controller controller = new Controller(service, TestUtil.genScanner(deleteSuccessSimulationInput));
        controller.run();
        String result = output.toString().trim();
        assertTrue(result.contains(id + "번 명언 삭제 완료"));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 삭제실패테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        String id = deleteFailSimulationInput.split("\n")[0].split("=")[1];

        Controller controller = new Controller(service, TestUtil.genScanner(deleteFailSimulationInput));
        controller.run();
        String result = output.toString().trim();
        assertTrue(result.contains(id + "번 명언은 존재하지 않습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 수정성공테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        String id = updateSuccessSimulationInput.split("\n")[3].split("=")[1];

        Controller controller = new Controller(service, TestUtil.genScanner(updateSuccessSimulationInput));
        controller.run();
        String result = output.toString().trim();
        assertTrue(result.contains(id + "번 명언 수정 완료"));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 수정실패테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        String id = updateFailSimulationInput.split("\n")[0].split("=")[1];

        Controller controller = new Controller(service, TestUtil.genScanner(updateFailSimulationInput));
        controller.run();
        String result = output.toString().trim();
        assertTrue(result.contains(id + "번 명언은 존재하지 않습니다."));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 목록테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        Controller controller = new Controller(service, TestUtil.genScanner(listSimulationInput));
        controller.run();

        String result = output.toString().trim();

        assertTrue(result.contains("1 / 작가1 / 명언1"));
        assertTrue(result.contains("2 / 작가2 / 명언2"));
        assertTrue(result.contains("명언 목록 출력 완료"));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 검색테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        Controller controller = new Controller(service, TestUtil.genScanner(searchSimulationInput));
        controller.run();

        String result = output.toString().trim();

        //assertTrue(result.contains("1 / 작가1 / 명언1"));

        TestUtil.clearSetOutToByteArray(output);
        System.out.println(result);
    }

    @Test
    void 빌드테스트() {
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        Controller controller = new Controller(service, TestUtil.genScanner(buildSimulationInput));
        controller.run();

        String result = output.toString().trim();
        assertTrue(result.contains("data.json 빌드 완료"));

        TestUtil.clearSetOutToByteArray(output);
    }

}