package com.ll.wiseSaying;


import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class WiseSayingController {
    private final Scanner scanner;
    private final WiseSayingService wiseSayingService;

    public WiseSayingController(Scanner scanner) {
        this.scanner = scanner;
        this.wiseSayingService = new WiseSayingService();
    }

    public void write() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying = wiseSayingService.create(content, author);
        System.out.printf("%d번 명언이 등록되었습니다.%n", wiseSaying.getId());
    }

    public void list(String query) {
        //입력이 목록 단독일 때
        int page = 1;

        if (query == null || query.isEmpty()) {
            List<WiseSaying> wiseSayings = wiseSayingService.getList();

            int totalCount = wiseSayings.size();
            int totalPages = (totalCount % 5 == 0) ? (totalCount / 5) : (totalCount / 5 + 1); // 5로 나누어떨어지면 totalCount/5 안나눠떨어지면 올림하려고

            List<WiseSaying> currentpage = wiseSayings.stream()
                                                    .sorted((a,b) -> b.getId()-a.getId()) // 역순으로 정렬후
                                                    .skip((page-1)*5) // 현재페이지를보여주기위해 앞쪽 스킵
                                                    .limit(5) // 5개까지만 보여주기 부족해도 5개이하로보여주고 5개이상이어도 5개에서 끊김
                                                    .collect(Collectors.toList());

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            for (WiseSaying wiseSaying : currentpage) {
                System.out.printf("%d / %s / %s\n",
                        wiseSaying.getId(),
                        wiseSaying.getAuthor(),
                        wiseSaying.getContent());
            }
            System.out.println("----------------------");
            System.out.printf("페이지 : [%d] / %d\n", page, totalPages);
            return;
        }
        // page = n
        if(query.startsWith("page")){
            //p-0,a-1,g-2,e-3,=-4 이라서 5번째부터 끝까지가 string으로 표현된 숫자임
            // string -> int로 변환 Integer.parsInt();
            List<WiseSaying> wiseSayings = wiseSayingService.getList();
            page = Integer.parseInt(query.substring(5));

            int totalCount = wiseSayings.size();
            int totalPages = (totalCount % 5 == 0) ? (totalCount / 5) : (totalCount / 5 + 1); // 5로 나누어떨어지면 totalCount/5 안나눠떨어지면 올림하려고

            List<WiseSaying> currentpage = wiseSayings.stream()
                    .sorted((a,b) -> b.getId()-a.getId()) // 역순으로 정렬후
                    .skip((page-1)*5) // 현재페이지를보여주기위해 앞쪽 스킵
                    .limit(5) // 5개까지만 보여주기 부족해도 5개이하로보여주고 5개이상이어도 5개에서 끊김
                    .collect(Collectors.toList());

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");
            System.out.println(page);
            for (WiseSaying wiseSaying : currentpage) {
                System.out.printf("%d / %s / %s\n",
                        wiseSaying.getId(),
                        wiseSaying.getAuthor(),
                        wiseSaying.getContent());
            }
            System.out.println("----------------------");
            System.out.printf("페이지 : [%d] / %d\n", page, totalPages);
            return;
        }

        //검색
         if(query.contains("&")) {
            String[] params = query.split("&");
            String keywordType = "";  // String 변수로 선언
            String keyword = "";      // String 변수로 선언
            for (String param : params) {
                String[] keyVal = param.split("=");
                if (keyVal[0].equals("keywordType")) {
                    keywordType = keyVal[1];  // 직접 값 할당
                } else if (keyVal[0].equals("keyword")) {
                    keyword = keyVal[1];      // 직접 값 할당
                }
            }

            List<WiseSaying> wiseSayings = wiseSayingService.getList();
            System.out.println("-".repeat(30));
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("-".repeat(30));
            System.out.println("번호 / 작가 / 명언");
            System.out.println("-".repeat(30));

            List<WiseSaying> searchResults = wiseSayingService.getListByKey(keywordType, keyword);
            for (WiseSaying wiseSaying : searchResults) {
                System.out.printf("%d / %s / %s\n",
                        wiseSaying.getId(),
                        wiseSaying.getAuthor(),
                        wiseSaying.getContent());
            }
            return;
        }


    }

    public void remove(String commandspecific) {
//        System.out.print("삭제할 명언 번호 : ");
//        int id = Integer.parseInt(scanner.nextLine());
        int removeId = Integer.parseInt(commandspecific.split("=")[1]);
        if (wiseSayingService.remove(removeId)) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(removeId));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(removeId));
        }
    }

    public void modify(String commandspecific) {
//        System.out.print("수정할 명언 번호 : ");
//        int id = Integer.parseInt(scanner.nextLine());
        int modifyId = Integer.parseInt(commandspecific.split("=")[1]);

        WiseSaying wiseSaying = wiseSayingService.get(modifyId);
        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(modifyId));
            return;
        }

        System.out.printf("명언(기존) : %s\n", wiseSaying.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        if (wiseSayingService.modify(modifyId, content, author)) {
            System.out.println("%d번 명언이 수정되었습니다.".formatted(modifyId));
        }
    }

    public void build(){
        wiseSayingService.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}