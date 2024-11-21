package baekgwa.global.data.domain;

import baekgwa.dto.ResponseDto;
import baekgwa.global.exception.CustomException;
import java.util.List;

public class PageableResponse<T> {
    private final List<T> data;
    private final int totalPages;
    private final int nowPages;
    private final boolean isLast;

    /**
     * Pageable 객체로, 알맞은 Pageable Response 객체를 만들어 줍니다.
     * @param pageable
     * @param dataList
     * @return
     * @param <T>
     */
    public static <T> PageableResponse<T> filtering(Pageable pageable, List<T> dataList) {
        int page = pageable.getPage() - 1;
        int pageSize = pageable.getSize();
        int totalItems = dataList.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);

        if (startIndex >= totalItems) {
            throw new CustomException("없는 페이지 입니다. 총 페이지 수 : " + totalPages);
        }
        List<T> pagedData = dataList.subList(startIndex, endIndex);
        boolean isLast = (page + 1) >= totalPages;

        return new PageableResponse<>(pagedData, totalPages, page + 1, isLast);
    }

    public PageableResponse(List<T> data, int totalPages, int nowPages, boolean isLast) {
        this.data = data;
        this.totalPages = totalPages;
        this.nowPages = nowPages;
        this.isLast = isLast;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public int getNowPages() {
        return nowPages;
    }

    public boolean isLast() {
        return isLast;
    }
}
