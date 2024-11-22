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
    public void StrPrint(String str, boolean IsLineBreak){
        if(IsLineBreak)
            System.out.println(str);
        else
            System.out.print(str);

    }
    public void StrPrint(String[] str, boolean IsLineBreak){
        for(String i : str) {
            if(i  == null) continue;
            if (IsLineBreak)
                System.out.println(i);
            else
                System.out.print(i);
        }

    }
    public void PrintTitle(){
        StrPrint("번호 / 작가 / 명언",true);
        StrPrint("-----------------", true);
    }
    public String GetString(String str){
        StrPrint(str,false);
        if(!scanner.hasNextLine()) return "종료";
        String result = scanner.nextLine();;
        return result;
    }

    public int CheckInputContent(){
        String str = GetString("명령)");
        if(str.equals("등록")) {
            StrPrint(service.WiseInert(GetString("명언 : "), GetString("작가 : ")),true);
            return 0;
        };

        if (str.equals("종료")){
            StrPrint("종료",true);
            return service.WiseExit();
        }
        else if (str.contains("목록")){
            PrintTitle();
            StrPrint(service.WiseList(str),true);
        }
        else if (str.contains("삭제")) {
            StrPrint(service.WiseDelete(str),true);
        }
        else if (str.contains("수정")){
            StrPrint(service.WiseChange(str,GetString("명언 : "),GetString("작가 : ")),true);
        }
        else if (str.equals("빌드")) {
            StrPrint(service.WiseBuild(),true);
        }
        else if (str.equals("로드")) {
            StrPrint(service.WiseLoad(),true);
        }
        return 0;
    }


    public void run(){
        int chk = 0;
        while(chk == 0) {
            chk = CheckInputContent();
        }
    }

}
