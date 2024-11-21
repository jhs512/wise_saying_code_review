package baekgwa.controller;

import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindList;
import baekgwa.global.data.domain.Pageable;
import baekgwa.global.data.domain.PageableResponse;
import baekgwa.global.data.domain.Search;
import baekgwa.global.util.ControllerUtils;
import baekgwa.service.WiseSayingService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WiseSayingControllerImpl implements WiseSayingController {

    private final BufferedReader bufferedReader;
    private final WiseSayingService wiseSayingService;

    public WiseSayingControllerImpl(BufferedReader bufferedReader,
            WiseSayingService wiseSayingService) {
        this.bufferedReader = bufferedReader;
        this.wiseSayingService = wiseSayingService;
    }

    @Override
    public void createDirectories() throws IOException {
        wiseSayingService.createDirectories();
    }

    @Override
    public void register() throws IOException {
        System.out.print("명언 : ");
        String content = bufferedReader.readLine();
        System.out.print("작가 : ");
        String author = bufferedReader.readLine();
        RequestDto.Register data = new RequestDto.Register(author, content);
        Long index = wiseSayingService.registerWiseSaying(data);
        System.out.println(index + "번 명언이 등록되었습니다.");
    }

    @Override
    @Deprecated(since = "13단계", forRemoval = false)
    public void printAll() throws IOException {
        List<FindList> findLists = wiseSayingService.findAllWiseSaying();

        if (!findLists.isEmpty()) {
            System.out.println("----------------------");
            for (ResponseDto.FindList findList : findLists) {
                System.out.println(findList);
            }
        }
    }

    @Override
    public void delete(Long id) throws IOException {
        wiseSayingService.deleteWiseSaying(id);
        System.out.println(id + "번 명언이 삭제되었습니다.");
    }

    @Override
    public void modifyWiseSaying(Long id) throws IOException {
        ResponseDto.FindSayingInfo findData = wiseSayingService.findWiseSayingInfo(id);
        System.out.println("명언(기존) : " + findData.getContent());
        System.out.print("명언 : ");
        String newContent = bufferedReader.readLine();

        System.out.println("작가(기존) : " + findData.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = bufferedReader.readLine();

        wiseSayingService.modifyWiseSaying(
                new ResponseDto.ModifyInfo(newAuthor, newContent, findData.getId()));
    }

    @Override
    public void build() throws IOException {
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }

    @Override
    public void search(Map<String, String> requestParams) throws IOException {
        // Step1) Params Converting
        Pageable pageable = ControllerUtils.createPageable(requestParams);
        Search searchParams = ControllerUtils.createSearch(requestParams);

        // Step2) Search By Converted Data
        PageableResponse<ResponseDto.FindList> findData
                = wiseSayingService.search(searchParams, pageable);

        printSearchData(searchParams, findData);
        printPage(findData);
    }

    private static void printSearchData(Search searchParams, PageableResponse<FindList> findData) {
        if (!searchParams.isEmpty()) {
            //필터링 들어가는 경우, 추가 출력 필요.
            System.out.println("----------------------");
            System.out.println("검색타입 : " + searchParams.getKeywordType());
            System.out.println("검색어 : " + searchParams.getKeyword());
            System.out.println("----------------------");
        }
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (FindList findList : findData.getData()) {
            System.out.println(findList);
        }
    }

    private static void printPage(PageableResponse<FindList> findData) {
        StringBuilder sb = new StringBuilder("페이지: ");
        for (int now = 1; now <= findData.getTotalPages(); now++) {
            if (now == findData.getNowPages()) {
                sb.append("[").append(now).append("]"); // 현재 페이지는 대괄호로 표시
            } else {
                sb.append(now);
            }
            if (now < findData.getTotalPages()) {
                sb.append(" / ");
            }
        }
        System.out.println(sb);
    }
}
