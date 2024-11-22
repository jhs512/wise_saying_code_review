//package baekgwa.entity;
//
//import baekgwa.wisesaying.entity.WiseSaying;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class WiseSayingTest {
//
//    @DisplayName("HashCode 와 Equals 로 객체의 동등성을 비교합니다.")
//    @Test
//    void hashAndEquals() {
//        // given
//        WiseSaying wiseSaying = new WiseSaying(1L, "content", "author");
//        WiseSaying sameWiseSaying = new WiseSaying(1L, "content", "author");
//        WiseSaying diffWiseSaying1 = new WiseSaying(2L, "content", "author");
//        WiseSaying diffWiseSaying2 = new WiseSaying(1L, "더미 명언", "author");
//        WiseSaying diffWiseSaying3 = new WiseSaying(1L, "content", "더미 작가");
//
//        // when // then
//        Assertions.assertTrue(wiseSaying.equals(sameWiseSaying));
//        Assertions.assertFalse(wiseSaying.equals(diffWiseSaying1));
//        Assertions.assertFalse(wiseSaying.equals(diffWiseSaying2));
//        Assertions.assertFalse(wiseSaying.equals(diffWiseSaying3));
//    }
//}