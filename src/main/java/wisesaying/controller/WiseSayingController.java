package wisesaying.controller;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import wisesaying.domain.WiseSaying;
import wisesaying.service.WiseSayingService;

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

	public void findAll() {
		LinkedList<WiseSaying> wiseSayingLinkedList = wiseSayingService.findAll();
		System.out.println("번호 / 작가 / 명언");

		wiseSayingLinkedList.forEach((wiseSaying) -> {
			System.out.printf("%d / %s / %s \n",
				wiseSaying.getId(), wiseSaying.getWriter(), wiseSaying.getWiseSaying());
		});

	}

	public void update() {
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

			Scanner sc = new Scanner(System.in);

			String select = sc.nextLine();

			if (select.equals("종료")) {
				sc.close();
				build();
				break;
			}

			switch (select) {
				case "등록":
					add();
					break;
				case "목록":
					findAll();
					break;
				case "삭제":
					delete();
					break;
				case "수정":
					update();
					break;
				case "빌드":
					build();
					break;
				default:
					System.out.println("잘못된 명령입니다.");
			}
		}
	}
}
