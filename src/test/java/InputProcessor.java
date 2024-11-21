import java.util.Scanner;

public class InputProcessor {

    private int quoteCount = 0; // 등록된 명언 수를 추적

    public void processInputAndPrint() {
        Scanner scanner = new Scanner(System.in);

        // 입력 읽기
        String command = scanner.nextLine();
        if ("등록".equals(command)) {
            String quote = scanner.nextLine();
            String author = scanner.nextLine();

            // 출력 결과
            quoteCount++;
            System.out.println("명언 : " + quote);
            System.out.println("작가 : " + author);
            System.out.println(quoteCount + "번 명언이 등록되었습니다.");
        }
    }
}
