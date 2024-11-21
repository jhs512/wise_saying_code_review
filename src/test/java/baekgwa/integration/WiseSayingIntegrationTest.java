package baekgwa.integration;

import static baekgwa.global.GlobalVariable.BUILD_FILE;
import static baekgwa.global.GlobalVariable.DB_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import baekgwa.entity.WiseSaying;
import baekgwa.supporter.IntegrationTestSupporter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 통합 테스트 진행. Controller => Service => Repository 입력 -> 표준입출력 리다이렉팅
 */
public class WiseSayingIntegrationTest extends IntegrationTestSupporter {

    @BeforeEach
    void init() throws IOException {
        initServiceAndRepository();
        wiseSayingRepository.createDirectory();
        wiseSayingRepository.deleteAllData();
    }

    @AfterEach
    void clean() throws IOException {
        wiseSayingRepository.deleteAllData();
    }

    @DisplayName("새로운 명언을 등록합니다.")
    @Test
    void registerWiseSaying() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut
                = initController("""
                새로운 명언
                작가 강성욱
                """, outputStream);

        try {
            // when
            wiseSayingControllerImpl.register();

            // then
            List<WiseSaying> findList = wiseSayingRepository.loadAllWiseSaying();
            assertEquals(1, findList.size());
            WiseSaying findData = findList.getFirst();
            assertNotNull(findData.getId());
            assertEquals("새로운 명언", findData.getContent());
            assertEquals("작가 강성욱", findData.getAuthor());
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("초기에 폴더를 생성합니다.")
    @Test
    void createDirectories() {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        try {
            // when
            wiseSayingControllerImpl.createDirectories();

            // then
            Path dirPath = Paths.get(DB_PATH);
            assertTrue(Files.exists(dirPath));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            afterDetach(originalOut);
        }
    }

    @Disabled("기능 변경으로 미사용")
    @DisplayName("저장된 모든 명언을 출력합니다.")
    @Test
    void printAllTest() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);
        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(2L, "2번 명언", "2번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(3L, "3번 명언", "3번 작가"));

        try {
            // when
            wiseSayingControllerImpl.printAll();

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("3 / 3번 작가 / 3번 명언"));
            assertTrue(result.contains("2 / 2번 작가 / 2번 명언"));
            assertTrue(result.contains("1 / 1번 작가 / 1번 명언"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @Disabled("기능 변경으로 미사용")
    @DisplayName("저장된 명언이 없다면 아무것도 출력하지 않습니다.")
    @Test
    void printAllTest2() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        try {
            // when
            wiseSayingControllerImpl.printAll();

            // then
            String result = outputStream.toString();
            assertFalse(result.contains("번 작가"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("저장된 명언을 ID를 통해 삭제 합니다.")
    @Test
    void deleteTest1() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);
        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(2L, "2번 명언", "2번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(3L, "3번 명언", "3번 작가"));

        try {
            // when
            wiseSayingControllerImpl.delete(1L);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("1번 명언이 삭제되었습니다."));
            List<WiseSaying> findList = wiseSayingRepository.loadAllWiseSaying();
            assertEquals(2, findList.size());
            assertFalse(findList.contains(new WiseSaying(1L, "1번 명언", "1번 작가")));
            assertTrue(findList.contains(new WiseSaying(2L, "2번 명언", "2번 작가")));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("없는 ID를 삭제하려고 하면, 오류 메세지를 발행 하고, 삭제 작업을 하지 않습니다.")
    @Test
    void deleteTest2() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);
//        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(2L, "2번 명언", "2번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(3L, "3번 명언", "3번 작가"));

        try {
            // when
            wiseSayingControllerImpl.delete(1L);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("1번 명언은 존재하지 않습니다."));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("id를 통해, 명언을 수정 합니다.")
    @Test
    void modifyWiseSayingTest() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("수정된 명언\n백과", outputStream);
        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));

        try {
            // when
            wiseSayingControllerImpl.modifyWiseSaying(1L);

            // then
            Optional<WiseSaying> findOptionalData = wiseSayingRepository.findById(1L);
            assertTrue(findOptionalData.isPresent());
            WiseSaying findData = findOptionalData.get();
            assertEquals(findData, new WiseSaying(1L, "수정된 명언", "백과"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("없는 ID로 수정하려고 하면, 오류 메세지를 서빙하고 작업을 취소합니다.")
    @Test
    void modifyWiseSayingTest2() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("수정된 명언\n백과", outputStream);
//        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));

        try {
            // when
            wiseSayingControllerImpl.modifyWiseSaying(1L);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("1번 명언은 존재하지 않습니다."));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("모든 명언들을 사용해서 data.json 을 만드는 빌드 작업을 수행합니다.")
    @Test
    void buildTest() throws IOException {
        // init // given
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);
        wiseSayingRepository.saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(2L, "2번 명언", "2번 작가"));
        wiseSayingRepository.saveWiseSaying(new WiseSaying(3L, "3번 명언", "3번 작가"));

        try {
            // when
            wiseSayingControllerImpl.build();

            // then
            Path dataJsonPath = Paths.get(DB_PATH + BUILD_FILE);
            assertTrue(Files.exists(dataJsonPath));
            String result = Files.readString(dataJsonPath);
            assertTrue(result.contains("2번 명언"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("검색을 실시합니다. 파라미터 없이 기본 조회할 경우, 0번 페이지가 확인됩니다.")
    @Test
    void searchTest1() {
        // given

        // stubbing

        // when

        // then
    }
}
