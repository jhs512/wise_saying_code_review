package baekgwa.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import baekgwa.global.data.domain.Pageable;
import baekgwa.global.data.domain.Search;
import baekgwa.global.database.TestDataSourceConfig;
import baekgwa.global.exception.CustomException;
import baekgwa.wisesaying.entity.WiseSaying;
import baekgwa.supporter.IntegrationTestSupporter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
            Path dirPath = Paths.get(TestDataSourceConfig.DB_PATH);
            assertTrue(Files.exists(dirPath));

        } catch (IOException e) {
            throw new RuntimeException(e);
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

        for(long i=1L; i<=3; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

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

        for(long i=2L; i<=3; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when // then
            CustomException customException = assertThrows(CustomException.class, () -> {
                wiseSayingControllerImpl.delete(1L);
            });
            assertEquals("1번 명언은 존재하지 않습니다.", customException.getMessage());
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
        saveWiseSaying(new WiseSaying(1L, "1번 명언", "1번 작가"));

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

        try {
            // when // then
            CustomException customException = assertThrows(CustomException.class, () -> {
                wiseSayingControllerImpl.modifyWiseSaying(1L);
            });
            assertEquals("1번 명언은 존재하지 않습니다.", customException.getMessage());
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

        for(long i=1L; i<=3; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when
            wiseSayingControllerImpl.build();

            // then
            Path dataJsonPath = Paths.get(TestDataSourceConfig.DB_PATH + TestDataSourceConfig.BUILD_FILE);
            assertTrue(Files.exists(dataJsonPath));
            String result = Files.readString(dataJsonPath);
            assertTrue(result.contains("2번 명언"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("검색을 실시합니다. 파라미터 없이 기본 조회할 경우, 1번 페이지가 확인됩니다.")
    @Test
    void searchTest1() throws IOException {
        // init
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        // given
        //비어있는 Request Params 생성
        Map<String, String> requestParams = new HashMap<>();
        for(long i=1L; i<=10; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when
            wiseSayingControllerImpl.search(requestParams);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("페이지 : [1] / 2"));
            assertTrue(result.contains("10 / 10번 작가 / 10번 명언"));
            assertFalse(result.contains("검색타입 : "));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("검색을 실시합니다. Page 와 Size 설정이 가능합니다.")
    @Test
    void searchTest2() throws IOException {
        // init
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        // given
        //비어있는 Request Params 생성
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(Pageable.PAGE, "2");
        requestParams.put(Pageable.SIZE, "3");
        for(long i=1L; i<=10; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when
            wiseSayingControllerImpl.search(requestParams);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("페이지 : 1 / [2] / 3 / 4"));
            assertTrue(result.contains("7 / 7번 작가 / 7번 명언"));
            assertFalse(result.contains("검색타입 : "));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("검색을 실시합니다. author 와 content 에 대해, 키워드 검색이 가능해야 합니다.")
    @Test
    void searchTest3() throws IOException {
        // init
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        // given
        //비어있는 Request Params 생성
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(Search.KEYWORD, "10번");
        requestParams.put(Search.KEYWORD_TYPE, "author");
        for(long i=1L; i<=10; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when
            wiseSayingControllerImpl.search(requestParams);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("페이지 : [1]"));
            assertTrue(result.contains("10 / 10번 작가 / 10번 명언"));
            assertTrue(result.contains("검색타입 : author"));
            assertTrue(result.contains("검색어 : 10번"));
        } finally {
            afterDetach(originalOut);
        }
    }

    @DisplayName("검색을 실시합니다. 키워드 검색과, 페이징은 동시에 가능해야 합니다.")
    @Test
    void searchTest4() throws IOException {
        // init
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = initController("", outputStream);

        // given
        //비어있는 Request Params 생성
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(Search.KEYWORD, "1");
        requestParams.put(Search.KEYWORD_TYPE, "content");
        requestParams.put(Pageable.PAGE, "2");
        requestParams.put(Pageable.SIZE, "3");
        for(long i=1L; i<=100; i++) {
            saveWiseSaying(new WiseSaying(i, i+"번 명언", i+"번 작가"));
        }

        try {
            // when
            wiseSayingControllerImpl.search(requestParams);

            // then
            String result = outputStream.toString();
            assertTrue(result.contains("페이지 : 1 / [2] / 3 / 4 / 5 / 6 / 7"));
            assertTrue(result.contains("71 / 71번 작가 / 71번 명언"));
            assertTrue(result.contains("검색타입 : content"));
            assertTrue(result.contains("검색어 : 1"));
        } finally {
            afterDetach(originalOut);
        }
    }

    private void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        wiseSayingRepository.saveWiseSaying(wiseSaying);
    }
}
