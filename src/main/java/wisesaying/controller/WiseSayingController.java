package wisesaying.controller;

import java.util.Scanner;

import wisesaying.service.WiseSayingService;

public class WiseSayingController {
	private final WiseSayingService wiseSayingService;

	public WiseSayingController(WiseSayingService wiseSayingService) {
		this.wiseSayingService = wiseSayingService;
	}

	public void add(Scanner sc) {
		System.out.print("명언 : ");
		String wiseSaying = sc.nextLine();

		System.out.print("작가 : ");
		String writer = sc.nextLine();

		Long addId = wiseSayingService.add(wiseSaying, writer);

		String message = addId == 0L ? "명언 등록이 실패됐습니다." : addId + "번 명언이 등록되었습니다.";

		System.out.println(message);
	}
}
