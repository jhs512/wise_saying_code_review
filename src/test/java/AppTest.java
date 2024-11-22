import com.ll.wiseSaying.App;

import java.io.File;

public class AppTest {
    // 입력과 출력 리다이렉션을 이용해 테스트 실행
    public static String run(final String input) {
        // 입력 리다이렉션
        final var scanner = TestUtil.genScanner(input); // 입력 데이터를 스캐너로 생성

        // 출력 리다이렉션
        final var output = TestUtil.setOutToByteArray(); // System.out 출력을 ByteArrayOutputStream으로 변경

        // 실행
        try {
            new App(scanner).run(); // App 클래스 실행 (scanner로 입력 제공)
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage()); // 실행 중 예외 발생 시 오류 메시지 출력
        }

        // 출력 데이터 가져오기
        final String result = output.toString(); // ByteArrayOutputStream에 저장된 출력 데이터를 문자열로 변환

        // 출력 복원
        TestUtil.clearSetOutToByteArray(output); // System.out 출력을 원래 상태로 복원

        return result.trim(); // 결과 문자열 반환 (양 끝 공백 제거)
    }

    // 테스트 환경 초기화
    public static void clear() {
        File file = new File("db/data.json"); // 데이터 파일 경로
        if (file.exists()) {
            if (!file.delete()) { // 파일이 존재하면 삭제
                System.err.println("파일 삭제 실패: " + file.getAbsolutePath()); // 삭제 실패 시 메시지 출력
            }
        }

        File directory = new File("db"); // 데이터 디렉토리 경로
        if (!directory.exists()) {
            if (!directory.mkdirs()) { // 디렉토리가 없으면 생성
                System.err.println("디렉토리 생성 실패: " + directory.getAbsolutePath()); // 생성 실패 시 메시지 출력
            }
        }
    }
}