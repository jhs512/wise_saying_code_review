package com.ll.wiseSaying;

import java.util.List;
/*

 - 순수 비즈니스 로직 담당
 - 데이터 저장/수정/삭제/조회 요청을 리포지토리에 전달


 */
public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }
    // 명언 등록
    public int create(String content, String author) {
        return wiseSayingRepository.save(new WiseSaying(content, author));
    }

    // 명언 조회
    public List<WiseSaying> findAll() {
        return wiseSayingRepository.findAll();
    }

    // 명언 삭제
    public boolean delete(int id) {
        return wiseSayingRepository.deleteById(id);
    }

    // 명언 수정
    public boolean update(int id, String content, String author) {
        return wiseSayingRepository.updateById(id, content, author);
    }
}