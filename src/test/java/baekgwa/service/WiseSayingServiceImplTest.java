//package baekgwa.service;
//
//import baekgwa.wisesaying.dto.RequestDto;
//import baekgwa.wisesaying.dto.ResponseDto;
//import baekgwa.supporter.ServiceTestSupporter;
//import baekgwa.wisesaying.service.WiseSayingService;
//import baekgwa.wisesaying.service.WiseSayingServiceImpl;
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class WiseSayingServiceImplTest extends ServiceTestSupporter {
//
//    private final WiseSayingService wiseSayingService = new WiseSayingServiceImpl(mockWiseSayingRepository);
//    private final WiseSayingService exceptionWiseSayingService = new WiseSayingServiceImpl(exceptionMockWiseSayingRepository);
//
//    @DisplayName("등록 완료 후, 등록한 id를 반환합니다.")
//    @Test
//    void registerWiseSaying() throws IOException {
//        // given
//        RequestDto.Register input = new RequestDto.Register(
//                baseWiseSaying.getAuthor(),
//                baseWiseSaying.getContent());
//
//        // when
//        Long id = wiseSayingService.registerWiseSaying(input);
//
//        // then
//        Assertions.assertEquals(baseWiseSaying.getId(), id);
//    }
//
//    // todo
//    @DisplayName("등록 중, 오류가 발생하면 작업이 취소되고, 에러 메세지가 출력됩니다.")
//    @Test
//    void exceptionRegisterWiseSaying() {
//    }
//
//    @DisplayName("등록된 모든 명언을 반환합니다. entity -> transfer 객체로 매핑됩니다.")
//    @Test
//    void findAllWiseSayingTest() throws IOException {
//        // given
//
//        // when
//        List<ResponseDto.FindList> findLists = wiseSayingService.findAllWiseSaying();
//
//        // then
//        Assertions.assertEquals(1, findLists.size());
//        Assertions.assertEquals(baseWiseSaying.getId(), findLists.getFirst().getId());
//    }
//
//    //todo:
//    @DisplayName("모든 명언을 조회하는 중, 오류가 발생하면, 오류메세지를 출력합니다.")
//    @Test
//    void exceptionFindAllWiseSayingTest() {
//    }
//
//    @DisplayName("Id로 특정 명언을 삭제합니다.")
//    @Test
//    void deleteWiseSayingTest1() throws IOException {
//        // given
//
//        // when
//        Boolean result = wiseSayingService.deleteWiseSaying(baseWiseSaying.getId());
//
//        // then
//        Assertions.assertTrue(result);
//    }
//
//    @DisplayName("존재하지 않은 ID를 삭제하려면, false 가 반환 됩니다.")
//    @Test
//    void deleteWiseSayingTest2() throws IOException {
//        // given
//
//        // when
//        Boolean result = wiseSayingService.deleteWiseSaying(999L);
//
//        // then
//        Assertions.assertFalse(result);
//    }
//
//    //todo
//    @DisplayName("명언을 삭제하는 중, 오류가 발생하면, 오류메세지를 출력합니다.")
//    @Test
//    void exceptionDeleteWiseSayingTest() {
//    }
//
//    @DisplayName("Id로 특정 명언을 조회합니다. Dto 로 매핑 됩니다.")
//    @Test
//    void findWiseSayingInfoTest() throws IOException {
//        // given
//
//        // when
//        Optional<ResponseDto.FindSayingInfo> findOptionalData = wiseSayingService.findWiseSayingInfo(
//                baseWiseSaying.getId());
//
//        // then
//        Assertions.assertTrue(findOptionalData.isPresent());
//        ResponseDto.FindSayingInfo findData = findOptionalData.get();
//        Assertions.assertEquals(findData.getId(), baseWiseSaying.getId());
//    }
//
//    @DisplayName("등록되지 않은 Id로 명언을 검색하면, 빈값 (Optional) 이 반환 됩니다. ")
//    @Test
//    void findWiseSayingInfoTest2() throws IOException {
//        // given
//
//        // when
//        Optional<ResponseDto.FindSayingInfo> findOptionalData = wiseSayingService.findWiseSayingInfo(2L);
//
//        // then
//        Assertions.assertTrue(findOptionalData.isEmpty());
//    }
//
//    //todo:
//    @DisplayName("id로 명언 도중 오류가 발생하면, 오류 메세지를 출력합니다.")
//    @Test
//    void exceptionFindWiseSayingInfoTest() {
//    }
//
//    //todo: 단위 테스트 할 부분 없음.
//    @Disabled
//    @DisplayName("특정 명언을 수정합니다.")
//    @Test
//    void modifyWiseSaying() {
//    }
//
//    //todo: 단위 테스트 할 부분 없음.
//    @Disabled
//    @DisplayName("경로를 생성 합니다.")
//    @Test
//    void createDirectoriesTest() {
//    }
//
//    //todo: 단위 테스트 할 부분 없음.
//    @Disabled
//    @DisplayName("build 를 수행합니다. build 는 모든 명언을 data.json 으로 만듭니다.")
//    @Test
//    void buildTest() {
//    }
//}