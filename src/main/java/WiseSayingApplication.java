import java.util.Scanner;

import service.WiseSayingService;

public class WiseSayingApplication {
	private static final WiseSayingService wiseSayingService = new WiseSayingService();

	public static void main(String[] args) {
		while(true) {
			System.out.println("== 명언 앱 ==");
			System.out.print("명령) ");
			Scanner sc = new Scanner(System.in);
			String select = sc.nextLine();
			if (select.equals("종료")) {
				break;
			}

			switch (select) {
				case "등록":
					wiseSayingService.add(sc);
					break;
				default:
					System.out.println("잘못된 명령입니다.");
			}
		}
	}
}
