package baekgwa.service;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.entity.WiseSaying;
import baekgwa.global.GlobalVariable;
import baekgwa.repository.WiseSayingRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingServiceImpl implements WiseSayingService {

    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingServiceImpl(WiseSayingRepository wiseSayingRepository) {
        this.wiseSayingRepository = wiseSayingRepository;
    }

    @Override
    public Long registerWiseSaying(RequestDto.Register data) {
        try {
            Long nextId = wiseSayingRepository.loadLastId() + 1;
            WiseSaying newWiseSayingData = new WiseSaying(nextId, data.getContent(), data.getAuthor());

            wiseSayingRepository.saveWiseSaying(newWiseSayingData);
            wiseSayingRepository.saveLastId(nextId);
            return nextId;
        } catch (IOException e) {
            System.out.println("명언 등록 과정 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
        return 0L;
    }

    @Deprecated(since = "13단계", forRemoval = false)
    @Override
    public List<ResponseDto.FindList> findAllWiseSaying() {
        try {
            List<WiseSaying> findWiseSayingList = wiseSayingRepository.loadAllWiseSaying();
            return findWiseSayingList.stream()
                    .map(entity -> new ResponseDto.FindList(
                            entity.getAuthor(),
                            entity.getContent(),
                            entity.getId()
                    ))
                    .toList();
        } catch (IOException e) {
            System.out.println("명언 목록 조회 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
        return List.of();
    }

    @Override
    public Boolean deleteWiseSaying(Long id) {
        try {
            wiseSayingRepository.deleteById(id);
        } catch (RuntimeException e) {
            return false;
        } catch (IOException e) {
            System.out.println("명언 삭제 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    @Override
    public void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo) {
        try {
            wiseSayingRepository.saveWiseSaying(
                    new WiseSaying(modifyInfo.getId(), modifyInfo.getContent(), modifyInfo.getAuthor()));
        } catch (IOException e) {
            System.out.println("명언 수정 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public Optional<ResponseDto.FindSayingInfo> findWiseSayingInfo(Long id) {
        try {
            Optional<WiseSaying> findOptionalData = wiseSayingRepository.findById(id);
            if(findOptionalData.isEmpty()){
                return Optional.empty();
            }
            WiseSaying findData = findOptionalData.get();
            return Optional.of(new ResponseDto.FindSayingInfo(
                    findData.getAuthor(), findData.getContent(), findData.getId()));
        } catch (IOException e) {
            System.out.println("명언 수정 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
        return Optional.empty();
    }

    @Override
    public void createDirectories() throws IOException {
        wiseSayingRepository.createDirectory();
    }

    @Override
    public void build() {
        try {
            wiseSayingRepository.build();
        } catch (IOException e) {
            System.out.println("빌드 중, 오류가 발생하였습니다. Message = " + e.getMessage());
            System.exit(0);
        }
    }

    //order에 넘어올 수 있는 것들
    //Pagable 용
        // key : Page | value : 0~N / (만약 범위를 벗어나면? Exception 발생시켜서 핸들링 하는걸로)
    //like 연산용
        // key : keywordType | value : author, content
        // key : keyword | value : AnyString
    @Override
    public List<ResponseDto.FindList> search(Map<String, String> orders) {
        List<ResponseDto.FindList> findLists = findAllWiseSaying();

        if(findLists.isEmpty()) return List.of();

        //1차. like 연산 필터링
        if (orders.containsKey(GlobalVariable.KEYWORD_TYPE) && !orders.get(GlobalVariable.KEYWORD_TYPE).isEmpty()) {
            String keywordType = orders.get(GlobalVariable.KEYWORD_TYPE);  // 'author' 또는 'content'
            String keyword = orders.get(GlobalVariable.KEYWORD);  // 검색할 키워드 값

            // keywordType에 맞춰 필터링
            if (keywordType.equals("author")) {
                findLists = findLists.stream()
                        .filter(data -> data.getAuthor().contains(keyword)) // 'author' 필드에 키워드가 포함된 데이터를 필터링
                        .toList();
            } else if (keywordType.equals("content")) {
                findLists = findLists.stream()
                        .filter(data -> data.getContent().contains(keyword)) // 'content' 필드에 키워드가 포함된 데이터를 필터링
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
