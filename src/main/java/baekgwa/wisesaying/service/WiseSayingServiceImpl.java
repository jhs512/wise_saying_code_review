package baekgwa.wisesaying.service;

import baekgwa.wisesaying.dto.RequestDto;
import baekgwa.wisesaying.dto.ResponseDto;
import baekgwa.wisesaying.dto.ResponseDto.FindList;
import baekgwa.wisesaying.entity.WiseSaying;
import baekgwa.global.data.domain.Pageable;
import baekgwa.global.data.domain.PageableResponse;
import baekgwa.global.data.domain.Search;
import baekgwa.global.exception.CustomException;
import baekgwa.wisesaying.repository.WiseSayingRepository;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

public class WiseSayingServiceImpl implements WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingServiceImpl(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    @Override
    public Long registerWiseSaying(RequestDto.Register data) throws IOException {
        Long nextId = wiseSayingRepository.loadLastId() + 1;
        WiseSaying newWiseSayingData = new WiseSaying(nextId, data.getContent(), data.getAuthor());

        wiseSayingRepository.saveWiseSaying(newWiseSayingData);
        wiseSayingRepository.saveLastId(nextId);
        return nextId;
    }

    @Deprecated(since = "13단계", forRemoval = false)
    @Override
    public List<ResponseDto.FindList> findAllWiseSaying() throws IOException {
        List<WiseSaying> findWiseSayingList = wiseSayingRepository.loadAllWiseSaying();
        return findWiseSayingList.stream()
                .map(entity -> new ResponseDto.FindList(
                        entity.getAuthor(),
                        entity.getContent(),
                        entity.getId()
                ))
                .toList();
    }

    @Override
    public void deleteWiseSaying(Long id) throws IOException {
        try {
            wiseSayingRepository.deleteById(id);
        }
        catch (NoSuchFileException e) {
            throw new CustomException(id + "번 명언은 존재하지 않습니다.");
        }
    }

    @Override
    public void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo) throws IOException {
        wiseSayingRepository.saveWiseSaying(
                new WiseSaying(modifyInfo.getId(), modifyInfo.getContent(),
                        modifyInfo.getAuthor()));
    }

    @Override
    public ResponseDto.FindSayingInfo findWiseSayingInfo(Long id) throws IOException {
        Optional<WiseSaying> findOptionalData = wiseSayingRepository.findById(id);
        if (findOptionalData.isEmpty()) {
            throw new CustomException(id + "번 명언은 존재하지 않습니다.");
        }
        WiseSaying findData = findOptionalData.get();
        return new ResponseDto.FindSayingInfo(
                findData.getAuthor(), findData.getContent(), findData.getId());
    }

    @Override
    public void createDirectories() throws IOException {
        wiseSayingRepository.createDirectory();
    }

    @Override
    public void build() throws IOException {
        wiseSayingRepository.build();
    }

    /**
     * @param searchParams
     * @param pageable
     * @return
     * @throws IOException
     */
    @Override
    public PageableResponse<ResponseDto.FindList> search(Search searchParams, Pageable pageable) throws IOException {
        List<ResponseDto.FindList> findLists = findAllWiseSaying();

        if (findLists.isEmpty()) {
            return new PageableResponse<>(List.of(), 0, pageable.getPage(), true);
        }

        // Step1) Search 객체로 Filtering 진행
        findLists = filteringBySearch(searchParams, findLists);

        // Step2) Pageable 객체로, PageableResponse 객체로 반환
        return PageableResponse.filtering(pageable, findLists);
    }

    private List<FindList> filteringBySearch(Search searchParams, List<FindList> findLists) {
        if(!searchParams.isEmpty()) {
            String keywordType = searchParams.getKeywordType();
            String keyword = searchParams.getKeyword();

            if (keywordType.equals("author")) {
                findLists = findLists.stream()
                        .filter(data -> data.getAuthor()
                                .contains(keyword)) // 'author' 필드에 키워드가 포함된 데이터를 필터링
                        .toList();
            } else if (keywordType.equals("content")) {
                findLists = findLists.stream()
                        .filter(data -> data.getContent()
                                .contains(keyword)) // 'content' 필드에 키워드가 포함된 데이터를 필터링
                        .toList();
            } else {
                throw new CustomException("`author`, `content` 외에는 검색이 불가능 합니다.");
            }
        }
        return findLists;
    }
}
