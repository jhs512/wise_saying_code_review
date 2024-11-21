package baekgwa.global.util;

import static baekgwa.global.data.domain.Pageable.*;
import static baekgwa.global.data.domain.Search.KEYWORD;
import static baekgwa.global.data.domain.Search.KEYWORD_TYPE;

import baekgwa.global.data.domain.Pageable;
import baekgwa.global.data.domain.Search;
import java.util.Map;

public class ControllerUtils {

    /**
     * 입력받은 requestParams 로, Pageable 객체를 만들어 줍니다.
     * Handling 내용
     * "page"
     * "size"
     * @param requestParams
     * @return
     */
    public static Pageable createPageable(Map<String, String> requestParams) {
        int page = Integer.parseInt(requestParams.getOrDefault(PAGE, DEFAULT_PAGE.toString()));
        int size = Integer.parseInt(requestParams.getOrDefault(SIZE, DEFAULT_SIZE.toString()));

        return new Pageable(page, size);
    }

    /**
     * 입력받은 requestParams 로, Search 객체를 만들어 줍니다.
     * Handling 내용
     * "keywordType"
     * "keyword"
     */
    public static Search createSearch(Map<String, String> requestParams) {
        if(!requestParams.containsKey(KEYWORD_TYPE) || !requestParams.containsKey(KEYWORD)) {
            //둘중 하나라도 비어있다면, 정상적인 조회가 불가능.
            //exception or 전체 검색 되도록 설정.
            //현재는 전체 검색되도록 기능구현.
            return Search.empty();
        }
        String keywordType = requestParams.get(KEYWORD_TYPE);
        String keyword = requestParams.get(KEYWORD);
        return new Search(keywordType, keyword);
    }
}
