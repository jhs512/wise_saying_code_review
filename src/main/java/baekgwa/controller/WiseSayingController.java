package baekgwa.controller;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindList;
import baekgwa.global.GlobalVariable;
import baekgwa.service.WiseSayingService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class WiseSayingController {

    private final BufferedReader bufferedReader;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(BufferedReader bufferedReader,
            WiseSayingService wiseSayingService) {
        this.bufferedReader = bufferedReader;
        this.wiseSayingService = wiseSayingService;
    }

    public void createDirectories() throws IOException {
        wiseSayingService.createDirectories();
    }

    public void register() throws IOException {
        System.out.print("명언 : ");
        String content = bufferedReader.readLine();
        System.out.print("작가 : ");
        String author = bufferedReader.readLine();
        RequestDto.Register data = new RequestDto.Register(author, content);
        Long index = wiseSayingService.registerWiseSaying(data);
        System.out.println(index + "번 명언이 등록되었습니다.");
    }

    @Deprecated(since = "13단계", forRemoval = false)
    public void printAll() {
        List<FindList> findLists = wiseSayingService.findAllWiseSaying();

        if (!findLists.isEmpty()) {
            System.out.println("----------------------");
            for (ResponseDto.FindList findList : findLists) {
                System.out.println(findList);
            }
        }
    }

    public void delete(Long id) {
        boolean result = wiseSayingService.deleteWiseSaying(id);

        if (!result) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    public void modifyWiseSaying(Long id) {
        try {
            Optional<ResponseDto.FindSayingInfo> findOptionalData = wiseSayingService.findWiseSayingInfo(
                    id);
            if (findOptionalData.isEmpty()) {
                System.out.println(id + "번 명언은 존재하지 않습니다.");
                return;
            }
            ResponseDto.FindSayingInfo findData = findOptionalData.get();
            System.out.println("명언(기존) : " + findData.getContent());
            System.out.print("명언 : ");
            String newContent = bufferedReader.readLine();

            System.out.println("작가(기존) : " + findData.getAuthor());
            System.out.print("작가 : ");
            String newAuthor = bufferedReader.readLine();

            wiseSayingService.modifyWiseSaying(
                    new ResponseDto.ModifyInfo(newAuthor, newContent, findData.getId()));
        } catch (IOException e) {
            System.out.println("수정 작업 중, 오류가 발생하였습니다. = " + e.getMessage());
            System.exit(0);
        }
    }

    public void build() {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    public void search(Map<String, String> orders) {
        List<FindList> findLists = wiseSayingService.search(orders);
        if(orders.containsKey(GlobalVariable.KEYWORD_TYPE) && orders.containsKey(GlobalVariable.KEYWORD)) {
            //필터링 들어가는 경우, 추가 출력 필요.
            System.out.println("----------------------");
            System.out.println("검색타입 : " + orders.get(GlobalVariable.KEYWORD_TYPE));
            System.out.println("검색어 : " + orders.get(GlobalVariable.KEYWORD));
            System.out.println("----------------------");
        }
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (FindList findList : findLists) {
            System.out.println(findList);
        }
    }
}
