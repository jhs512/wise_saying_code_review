import java.util.Scanner;

import wisesaying.service.WiseSayingService;

public class WiseSayingApplication {
	private static final WiseSayingService wiseSayingService = new WiseSayingService();

	public static void main(String[] args) {
		while(true) {
			System.out.println("== 명언 앱 ==");
			System.out.print("명령) ");

			Scanner sc = new Scanner(System.in);

			String select = sc.nextLine();

			if (select.equals("종료")) {
				sc.close();
				wiseSayingService.build();
				break;
			}

			switch (select) {
				case "등록":
					wiseSayingService.add(sc);
					break;
				case "목록":
					wiseSayingService.findAll();
					break;
				case "삭제":
					wiseSayingService.delete(sc);
					break;
				case "수정":
					wiseSayingService.update(sc);
					break;
				case "빌드":
					wiseSayingService.build();
					break;
				default:
					System.out.println("잘못된 명령입니다.");
			}
		}
	}
}
