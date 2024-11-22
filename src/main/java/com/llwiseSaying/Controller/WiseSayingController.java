package com.llwiseSaying.Controller;

import com.llwiseSaying.Service.WiseSayingService;
import com.llwiseSaying.State;
import com.llwiseSaying.WiseSaying;
import static com.llwiseSaying.App.br;

import java.io.IOException;
import java.util.List;


public class WiseSayingController {


    public WiseSayingService wiseSayingService=new WiseSayingService();

    String inputValue;


    public WiseSayingController() {
        init();
    }

    public State run(String inputValue) throws IOException {

        State state=State.PROCESS;
        this.inputValue=inputValue;

        if(inputValue ==null) { state=State.EXIT; }
        if(inputValue.equals("종료")) { exitProcess(); state= State.EXIT;}
        if(inputValue.equals("등록")) { enrollProcess(); }
        if(inputValue.startsWith("목록")) { viewProcess(); }
        if(inputValue.startsWith("삭제")) { deleteProcess(); }
        if(inputValue.startsWith("수정")) { modifyProcess(); }
        if(inputValue.equals("초기화")) { resetProcess(); state=State.EXIT; }

        return state;

    }

    void init() {

        wiseSayingService.init();
    }

    void enrollProcess() throws IOException {
        System.out.print("명언 : ");
        String content=br.readLine();
        System.out.print("작가 : ");
        String writer= br.readLine();
        //등록후 등록한 일련번호를 반환받는다
        int id=wiseSayingService.enrollWiseSaying(content,writer);
        System.out.println(id+"번 명언이 등록되었습니다.");

    }

    void viewProcess() {

        String cmd=inputValue;
        String keywordType="none";
        String keyword="none";
        int page=1;

        String [] splitQuery;
        splitQuery=cmd.split("\\?");

        for(String query : splitQuery) {

            if (query.equals("목록")) {
                continue;
            }
            if (query.startsWith("keywordType")) {
                String[] splitData;
                try {
                    splitData = wiseSayingService.separateKeywordTypeAndKeyword(query);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                keywordType = splitData[0];
                keyword = splitData[1];

                System.out.println("---------------------");
                System.out.println("검색타입 : " + keywordType);
                System.out.println("검색어 : " + keyword);
                System.out.println("---------------------");
            }

            if (query.startsWith("page")) {
                page=wiseSayingService.separatePage(query);
            }


        }

            System.out.println("번호 / 작가 / 명언");
            System.out.println("----------------------");

            List<String> wiseSayingList=wiseSayingService.getWiseSayingList(keywordType,keyword,page);
            //명언 리스트를 가져올때 마지막 요소에 총 페이지 수를 저장해 가져온다
            int totalPage=Integer.parseInt(wiseSayingList.getLast());
            wiseSayingList.removeLast();
            //최대 페이지수를 넘지않고 설정
            page=Math.min(totalPage,page);

            wiseSayingList.stream()
                    .forEach(s -> System.out.println(s));
            System.out.println("----------------------");
            System.out.println("페이지: "+page+" / ["+totalPage+"]");


    }

    void deleteProcess() {
        String cmd=inputValue;

        try {
            int id=wiseSayingService.containId(cmd);
            //삭제하며 삭제된 일련번호 반환
            wiseSayingService.deleteWiseSaying(id);
            System.out.println(id+"번 명언이 삭제되었습니다.");


        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    void modifyProcess() {
        String cmd=inputValue;

        try {
            int id=wiseSayingService.containId(cmd);
            WiseSaying modifyWiseSaying=wiseSayingService.findWiseSaying(id);
            System.out.println("명언(기존) : "+ modifyWiseSaying.getContent());
            System.out.print("명언 : ");
            String content= br.readLine();
            System.out.println("작가(기존) : " + modifyWiseSaying.getWriter());
            System.out.print("작가 : ");
            String writer= br.readLine();

            wiseSayingService.modifyWiseSaying(id,content,writer);

            System.out.println(id+"번 명언이 수정되었습니다.");
            //수정된 내용 db반영

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    void resetProcess() {
        wiseSayingService.reset();

    }

    void exitProcess() {
        wiseSayingService.saveId();
        System.out.println("프로그램 종료");

    }

    void saveId() {
        wiseSayingService.saveId();
    }

}
