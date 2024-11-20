package baekgwa.repository;

import baekgwa.entity.WiseSaying;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WiseSayingRepositoryImplTest {

    private final WiseSayingRepository wiseSayingRepository = new WiseSayingRepositoryImpl();

    @BeforeEach
    void initBeforeTest() throws IOException {
        wiseSayingRepository.createDirectory();
    }

    @AfterEach
    void initAfterTest() throws IOException {
        wiseSayingRepository.deleteAllData();
    }

    @DisplayName("명언 데이터를 .json 파일로 저장합니다.")
    @Test
    void saveWiseSaying() throws IOException {
        // given
        WiseSaying newWiseSaying = new WiseSaying(1L, "명언임", "강성욱");

        // when
        wiseSayingRepository.saveWiseSaying(newWiseSaying);

        // then
        List<WiseSaying> wiseSayings = wiseSayingRepository.loadAllWiseSaying();
        Assertions.assertTrue(wiseSayings.stream()
                .anyMatch(wiseSaying -> wiseSaying.equals(newWiseSaying)));
    }

    @DisplayName("모든 명언 데이터를 조회합니다. 출력 데이터는 `내림차순`으로 정렬 됩니다.")
    @Test
    void loadAllWiseSaying() throws IOException {
        // given
        WiseSaying newData1 = new WiseSaying(1L, "명언임", "강성욱");
        WiseSaying newData2 = new WiseSaying(2L, "진짜 명언", "킹성욱");
        wiseSayingRepository.saveWiseSaying(newData1);
        wiseSayingRepository.saveWiseSaying(newData2);

        // when
        List<WiseSaying> findWiseSayingList = wiseSayingRepository.loadAllWiseSaying();

        // then
        Assertions.assertEquals(2, findWiseSayingList.size());
        Assertions.assertTrue(findWiseSayingList.stream()
                .anyMatch(data -> data.equals(newData1)));
        Assertions.assertTrue(findWiseSayingList.stream()
                .anyMatch(data -> data.equals(newData2)));
    }

    @DisplayName("마지막 Index Id 를 저장합니다.")
    @Test
    void saveLastId() throws IOException {
        // given

        // when
        wiseSayingRepository.saveLastId(1L);

        // then
        Long index = wiseSayingRepository.loadLastId();
        Assertions.assertEquals(1L, index);
    }

    @DisplayName("마지막 Index Id를 조회합니다.")
    @Test
    void loadLastId() throws IOException {
        // given
        wiseSayingRepository.saveLastId(1L);

        // when
        Long index = wiseSayingRepository.loadLastId();

        // then
        Assertions.assertEquals(1L, index);
    }

    @DisplayName("인덱스로 저장된 명언을 삭제합니다.")
    @Test
    void deleteById() throws IOException {
        // given
        WiseSaying newWiseSaying = new WiseSaying(1L, "명언임", "강성욱");
        wiseSayingRepository.saveWiseSaying(newWiseSaying);

        // when
        wiseSayingRepository.deleteById(1L);

        // then
        List<WiseSaying> wiseSayingList = wiseSayingRepository.loadAllWiseSaying();
        Assertions.assertEquals(0, wiseSayingList.size());
    }

    @DisplayName("Id로 명언의 유무를 검색합니다.")
    @Test
    void existById() throws IOException {
        // given
        WiseSaying newWiseSaying = new WiseSaying(1L, "명언임", "강성욱");
        wiseSayingRepository.saveWiseSaying(newWiseSaying);

        // when
        boolean result = wiseSayingRepository.existById(1L);

        // then
        Assertions.assertTrue(result);
    }

    @DisplayName("Id로 명언 정보를 객체로 찾아옵니다.")
    @Test
    void findById() throws IOException {
        // given
        WiseSaying newWiseSaying = new WiseSaying(1L, "명언임", "강성욱");
        wiseSayingRepository.saveWiseSaying(newWiseSaying);

        // when
        Optional<WiseSaying> findData = wiseSayingRepository.findById(1L);

        // then
        Assertions.assertTrue(findData.isPresent());
        Assertions.assertEquals(newWiseSaying, findData.get());
    }

    @DisplayName("Id로 명언 정보를 찾는데, 없으면 Optional 이 반환 됩니다.")
    @Test
    void findByIdTest2() throws IOException {
        // given

        // when
        Optional<WiseSaying> findData = wiseSayingRepository.findById(1L);

        // then
        Assertions.assertTrue(findData.isEmpty());
    }

    @DisplayName("빌드를 진행하여, 모든 명언 정보가 들어있는 data.json 파일을 생성합니다.")
    @Test
    void buildTest() throws IOException {
        // given
        WiseSaying newWiseSaying1 = new WiseSaying(1L, "명언임", "강성욱");
        wiseSayingRepository.saveWiseSaying(newWiseSaying1);

        WiseSaying newWiseSaying2 = new WiseSaying(2L, "2번명언", "2번작가");
        wiseSayingRepository.saveWiseSaying(newWiseSaying2);

        // when
        wiseSayingRepository.build();

        // then
        Path filePath = Paths.get("db/wiseSaying/data.json");
        Assertions.assertTrue(Files.exists(filePath));
    }
}