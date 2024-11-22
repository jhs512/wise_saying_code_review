package WiseSayingController;

import WiseSayingService.WiseSayingService;

import java.util.Scanner;



public class WiseSayingController {
    private Scanner scanner;
    private WiseSayingService service;
    public WiseSayingController(WiseSayingService service){
        scanner = new Scanner(System.in);
        this.service = service;
    }
    public WiseSayingController(WiseSayingService service, Scanner sc){
        this.service = service;
        scanner = sc;
    }
    public void strPrint(String str, boolean IsLineBreak){
        if(IsLineBreak)
            System.out.println(str);
        else
            System.out.print(str);

    }
    public void strPrint(String[] str, boolean IsLineBreak){
        for(String i : str) {
            if(i  == null) continue;
            if (IsLineBreak)
                System.out.println(i);
            else
                System.out.print(i);
        }

    }
    public void printTitle(){
        strPrint("번호 / 작가 / 명언",true);
        strPrint("-----------------", true);
    }
    public String getString(String str){
        strPrint(str,false);
        if(!scanner.hasNextLine()) return "종료";
        String result = scanner.nextLine();;
        return result;
    }

    public int checkInputContent(){
        String str = getString("명령)");
        if(str.equals("등록")) {
            strPrint(service.wiseInert(getString("명언 : "), getString("작가 : ")),true);
            return 0;
        };

        if (str.equals("종료")){
            strPrint("종료",true);
            return service.wiseExit();
        }
        else if (str.contains("목록")){
            printTitle();
            strPrint(service.wiseList(str),true);
        }
        else if (str.contains("삭제")) {
            strPrint(service.wiseDelete(str),true);
        }
        else if (str.contains("수정")){
            strPrint(service.wiseChange(str, getString("명언 : "), getString("작가 : ")),true);
        }
        else if (str.equals("빌드")) {
            strPrint(service.wiseBuild(),true);
        }
        else if (str.equals("로드")) {
            strPrint(service.wiseLoad(),true);
        }
        return 0;
    }


    public void run(){
        int chk = 0;
        while(chk == 0) {
            chk = checkInputContent();
        }
    }

}
