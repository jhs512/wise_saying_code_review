package wisesaying.controller;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import wisesaying.domain.WiseSaying;
import wisesaying.service.WiseSayingService;
import wisesaying.service.response.WiseSayingPageResponse;
import wisesaying.util.WiseSayingCondition;

public class WiseSayingController {
	private final WiseSayingService wiseSayingService;
	private final Scanner sc;

	public WiseSayingController(WiseSayingService wiseSayingService, Scanner sc) {
		this.wiseSayingService = wiseSayingService;
		this.sc = sc;
	}

	public void add() {
		System.out.print("명언 : ");
		String wiseSaying = sc.nextLine();

		System.out.print("작가 : ");
		String writer = sc.nextLine();

		Long addId = wiseSayingService.add(wiseSaying, writer);

		String message = addId == 0L ? "명언 등록이 실패됐습니다." : addId + "번 명언이 등록되었습니다.";

		System.out.println(message);
	}

	public void findAll(String select) {
		WiseSayingCondition wiseSayingCondition = queryStringAsCondition(select);
		WiseSayingPageResponse wiseSayingPageResponse = wiseSayingService.findAll(wiseSayingCondition);
		System.out.println("번호 / 작가 / 명언");

		wiseSayingPageResponse.getWiseSayingLinkedList().forEach((wiseSaying) -> {
			System.out.printf("%d / %s / %s \n",
				wiseSaying.getId(), wiseSaying.getWriter(), wiseSaying.getWiseSaying());
		});

		System.out.printf("페이지 : %d / [%d]\n", wiseSayingPageResponse.getPageNum(), wiseSayingPageResponse.getPageSize());
	}

	public void update() {
		try {
			System.out.print("수정?id = ");
			Long target = sc.nextLong();
			sc.nextLine();

			WiseSaying targetWiseSaying = wiseSayingService.findById(target);

			System.out.println("명언 (기존) : " + targetWiseSaying.getWiseSaying());
			System.out.print("명언 : ");
			String updateWiseSaying = sc.nextLine();

			System.out.println("작가 (기존) : " + targetWiseSaying.getWriter());
			System.out.print("작가 : ");
			String updateWriter = sc.nextLine();

			wiseSayingService.update(targetWiseSaying, updateWiseSaying, updateWriter);
		} catch (InputMismatchException e) {
			System.out.println("숫자만 입력 가능합니다.");
		}

	}

	public void delete() {
		try {
			System.out.print("삭제?id = ");

			Long targetId = sc.nextLong();
			sc.nextLine();

			Optional<Long> deletedId = wiseSayingService.delete(targetId);

			deletedId.ifPresent((delete) -> {
				System.out.println(delete + "번 명언이 삭제되었습니다.");
			});
		} catch (InputMismatchException e) {
			System.out.println("숫자만 입력 가능합니다.");
		}

	}

	public void build() {
		Boolean result = wiseSayingService.build();
		if (result) {
			System.out.println("data.json 파일의 내용이 갱신되었습니다.");
		}
	}

	public void run() {
		while (true) {
			System.out.println("== 명언 앱 ==");
			System.out.print("명령) ");

			String select = sc.nextLine();

			if (select.equals("종료")) {
				sc.close();
				build();
				break;
			}

			if (select.equals("등록")) {
				add();
			} else if (select.contains("목록")) {
				findAll(select);
			} else if (select.equals("삭제")) {
				delete();
			} else if (select.equals("수정")) {
				update();
			} else if (select.equals("빌드")) {
				build();
			} else {
				System.out.println("잘못된 명령입니다.");
			}
		}
	}

	public WiseSayingCondition queryStringAsCondition(String select) {
		Predicate<String[]> predicate = parts ->
			(parts[0].equals("page") && Long.parseLong(parts[1]) > 0) ||
				(parts[0].equals("writer") || !parts[1].trim().isEmpty()) ||
				(parts[0].equals("wisesaying") || !parts[1].trim().isEmpty());

		if (select.contains("?")) {
			String substring = select.substring(select.indexOf("?") + 1);
			Map<String, String> map = Arrays.stream(substring.split("&"))
				.map(param -> param.split("="))
				.filter(predicate)
				.collect(Collectors.toMap(
					parts -> parts[0],
					parts -> parts.length > 1 ? parts[1] : ""
				));

			return WiseSayingCondition.createCondition(map);
		}
		return new WiseSayingCondition();
	}
}
