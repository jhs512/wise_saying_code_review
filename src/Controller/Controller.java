package Controller;

import Service.Service;

import java.util.Scanner;



public class Controller{
    private Scanner scanner;
    private Service service;
    private  boolean isTest;
    public Controller(){
        isTest = false;
        this.service = service;
    }
    public Controller(Service service){
        isTest = true;
        scanner = new Scanner(System.in);
        this.service = service;
    }
    public Controller(Service service,Scanner sc){
        isTest = true;
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
        String result = scanner.nextLine();;
        if(isTest) StrPrint(result,true);
        return result;
    }

    public int CheckInputContent(){
        String str = GetString("명령)");
        if(str.equals("등록")) return service.WiseInert(GetString("명언 : "),GetString("작가 : "));
        if (str.equals("종료")) return service.WiseExit();
        if (str.contains("목록")){
            PrintTitle();
            StrPrint(service.WiseList(str),true);
            return 0;
        };
        if (str.contains("삭제")) {
            StrPrint(service.WiseDelete(str),true);
            return 0;
        }
        if (str.contains("수정")){
            StrPrint(service.WiseChange(str,GetString("명언 : "),GetString("작가 : ")),true);
            return 0;
        }
        if (str.equals("빌드")) return service.WiseBuild();
        if (str.equals("불러오기")) return 0;
        return 0;
    }


    public void run(){
        int chk = 0;
        while(chk == 0) {
            chk = CheckInputContent();
        }
    }

    public boolean isTest() {
        return isTest;
    }
}
