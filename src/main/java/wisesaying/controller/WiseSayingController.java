package wisesaying.controller;

import java.util.Scanner;

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
		wiseSayingService.findAll();
	}

	public void update() {
		wiseSayingService.update(sc);
	}

	public void delete() {
		System.out.print("삭제?id = ");

		Long targetId = sc.nextLong();
		sc.nextLine();

		wiseSayingService.delete(targetId);
	}

	public void build() {
		wiseSayingService.build();
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
