import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import infrastructure.wisesaying.WiseSayingRepository;
import wisesaying.controller.WiseSayingController;
import wisesaying.service.WiseSayingService;

public class WiseSayingApplication {
	private static final ConcurrentHashMap<String, Object> container = init();
	private static final WiseSayingController wiseSayingController
		= (WiseSayingController)container.get(WiseSayingController.class.getSimpleName());

	public static void main(String[] args) {
		while(true) {
			System.out.println("== 명언 앱 ==");
			System.out.print("명령) ");

			Scanner sc = new Scanner(System.in);

			String select = sc.nextLine();

			if (select.equals("종료")) {
				sc.close();
				wiseSayingController.build();
				break;
			}

			switch (select) {
				case "등록":
					wiseSayingController.add(sc);
					break;
				case "목록":
					wiseSayingController.findAll();
					break;
				case "삭제":
					wiseSayingController.delete(sc);
					break;
				case "수정":
					wiseSayingController.update(sc);
					break;
				case "빌드":
					wiseSayingController.build();
					break;
				default:
					System.out.println("잘못된 명령입니다.");
			}
		}
	}

	public static ConcurrentHashMap<String, Object> init() {
		ConcurrentHashMap<String, Object> container = new ConcurrentHashMap<>();
		WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
		WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository);
		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService);

		container.put(WiseSayingRepository.class.getSimpleName(), wiseSayingRepository);
		container.put(WiseSayingService.class.getSimpleName(), wiseSayingService);
		container.put(WiseSayingController.class.getSimpleName(), wiseSayingController);
		return container;
	}
}
