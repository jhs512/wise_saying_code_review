package baekgwa.service;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.entity.WiseSaying;
import baekgwa.global.GlobalVariable;
import baekgwa.global.exception.CustomException;
import baekgwa.repository.WiseSayingRepository;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Map;
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

    //order에 넘어올 수 있는 것들
    //Pagable 용
    // key : Page | value : 0~N / (만약 범위를 벗어나면? Exception 발생시켜서 핸들링 하는걸로)
    //like 연산용
    // key : keywordType | value : author, content
    // key : keyword | value : AnyString
    @Override
    public List<ResponseDto.FindList> search(Map<String, String> orders) throws IOException {
        List<ResponseDto.FindList> findLists = findAllWiseSaying();

        if (findLists.isEmpty()) {
            return List.of();
        }

        //1차. like 연산 필터링
        if (orders.containsKey(GlobalVariable.KEYWORD_TYPE) && !orders.get(
                GlobalVariable.KEYWORD_TYPE).isEmpty()) {
            String keywordType = orders.get(GlobalVariable.KEYWORD_TYPE);  // 'author' 또는 'content'
            String keyword = orders.get(GlobalVariable.KEYWORD);  // 검색할 키워드 값

            // keywordType에 맞춰 필터링
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
            }
        }

        //2차. 페이지네이션
        int page = 0;
        int pageSize = 5;
        if (orders.containsKey("Page")) {
            page = Integer.parseInt(orders.get("Page"));
        }
        int startIndex = page * pageSize;
        int endIndex = Math.min((page + 1) * pageSize, findLists.size());

        if (startIndex >= findLists.size()) {
            throw new IllegalArgumentException("Page out of range");
        }
        findLists = findLists.subList(startIndex, endIndex);

        return findLists;
    }
}
