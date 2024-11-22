package wisesaying.service.response;

import java.util.LinkedList;

import wisesaying.domain.WiseSaying;

public class WiseSayingPageResponse {
	Long pageNum;
	Long pageSize;
	LinkedList<WiseSaying> wiseSayingLinkedList = new LinkedList<>();

	public WiseSayingPageResponse(Long pageNum, Long pageSize, LinkedList<WiseSaying> wiseSayingLinkedList) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.wiseSayingLinkedList = wiseSayingLinkedList;
	}

	public static WiseSayingPageResponse createEmptyPageResponse() {
		return new WiseSayingPageResponse(0L, 0L, new LinkedList<>());
	}

	public Long getPageNum() {
		return pageNum;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public LinkedList<WiseSaying> getWiseSayingLinkedList() {
		return wiseSayingLinkedList;
	}
}
