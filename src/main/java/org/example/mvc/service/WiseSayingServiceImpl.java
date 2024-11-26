package org.example.mvc.service;

import org.example.constants.GlobalConstants;
import org.example.entity.WiseSaying;
import org.example.mvc.repository.WiseSayingRepository;
import org.example.utils.Parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingServiceImpl implements WiseSayingService {
    WiseSayingRepository wiseSayingRepository;


    public WiseSayingServiceImpl(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    @Override
    public void initRepository() {
        wiseSayingRepository.createDirectory();
        wiseSayingRepository.initRepository();
    }

    @Override
    // 명언 등록
    public WiseSaying register(String content, String author) throws IOException {

        // 레포지토리 저장
        WiseSaying wiseSaying = wiseSayingRepository.save(new WiseSaying(content, author));

        // json 파일 저장
        wiseSayingRepository.storeJson(wiseSaying);

        // 최종 등록한 id 저장 파일 수정
        int register_id = wiseSaying.getId();
        wiseSayingRepository.storeLastId(register_id);

        return wiseSaying;
    }

    // 명언 삭제
    @Override
    public boolean delete(int id) {
        // 존재하는 id 인지 확인하고 없다면 false 반환
        Optional<WiseSaying> optional = wiseSayingRepository.findById(id);
        if (optional.isEmpty())
            return false;

        // repository에서 삭제
        wiseSayingRepository.deleteById(id);

        return true;
    }

    // 파일 전체 삭제
    @Override
    public boolean deleteAll() {
        // 존재하는 id 인지 확인하고 없다면 false 반환
        List<WiseSaying> wiseSayingList = wiseSayingRepository.findAll();

        // repository에서 삭제
        wiseSayingList.stream()
                .mapToInt(wiseSaying -> wiseSaying.getId())
                .forEach(id -> {
                    wiseSayingRepository.deleteById(id);
                    wiseSayingRepository.deleteJsonById(id);});

        wiseSayingRepository.deleteBuildFile();
        wiseSayingRepository.deleteLastIdFile();
        wiseSayingRepository.setId(0);
        return true;
    }
    // id 로 명언 찾기
    @Override
    public Optional<WiseSaying> findWiseSayingById(int id) {
        return wiseSayingRepository.findById(id);
    }

    // 명언 수정
    @Override
    public void update(int id, String content, String author) {
        WiseSaying updatedWiseSaying = new WiseSaying(id, content, author);

        // 레포지토리에 수정
        wiseSayingRepository.updateById(id, updatedWiseSaying);
        // 수정된 json 파일 저장
        wiseSayingRepository.storeJson(updatedWiseSaying);
    }

    // 검색 및 목록 찾아내기
    @Override
    public List<WiseSaying> search(String command) {

        // 전체 명언 목록 조회
        List<WiseSaying> wiseSayingList = wiseSayingRepository.findAll();

        List<WiseSaying> sortedWiseSayingList = wiseSayingList.stream()
                .sorted((o1, o2) -> o2.getId() - o1.getId())
                .toList();

        if(command.equals("목록"))
            return sortedWiseSayingList;

        // 검색할 keyword, keywordType 을 파싱해서 획득
        Map<String, String> requestParams = Parser.extractKeywordsFromCommand(command);

        String keywordType = requestParams.getOrDefault(GlobalConstants.KEYWORD_TYPE, "");
        String keyword = requestParams.getOrDefault(GlobalConstants.KEYWORD, "");

        if (!keywordType.isEmpty() && !keyword.isEmpty()) {
            if (keywordType.equals(GlobalConstants.AUTHOR)) {
                return sortedWiseSayingList.stream()
                        .filter(wiseSaying -> wiseSaying.getAuthor().contains(keyword))
                        .toList();
            } else {
                return sortedWiseSayingList.stream()
                        .filter(wiseSaying -> wiseSaying.getContent().contains(keyword))
                        .toList();
            }
        } else {
            return sortedWiseSayingList;
        }
    }

    @Override
    public void build() {
        wiseSayingRepository.build();
    }

}
