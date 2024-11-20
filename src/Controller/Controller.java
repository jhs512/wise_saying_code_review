package Controller;

import java.util.Scanner;


import Service.Service;

public class Controller extends  Service{
    static public void StrPrint(String str, boolean IsLineBreak){
        if(IsLineBreak)
            System.out.println(str);
        else
            System.out.print(str);

    }
    static public void StrPrint(String[] str, boolean IsLineBreak){
        for(String i : str) {
            if(i  == null) continue;
            if (IsLineBreak)
                System.out.println(i);
            else
                System.out.print(i);
        }

    }
    static public void PrintTitle(){
        StrPrint("번호 / 작가 / 명언",true);
        StrPrint("-----------------", true);
    }
    static  public String GetString(String str){
        StrPrint(str,false);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int CheckInputContent(){
        String str = GetString("명령)");
        if(str.equals("등록")) return WiseInert(GetString("명언 : "),GetString("작가 : "));
        if (str.equals("종료")) return WiseExit();
        if (str.equals("목록")){
            PrintTitle();
            StrPrint(WiseList(str),true);
            return 0;
        };
        if (str.contains("삭제")) {
            StrPrint(WiseDelete(str),true);
            return 0;
        }
        if (str.contains("수정")){
            StrPrint(WiseChange(str,GetString("명언 : "),GetString("작가 : ")),true);
            return 0;
        }
        if (str.equals("빌드")) return WiseBuild();
        if (str.equals("불러오기")) return 0;
        return 0;
    }
    static  public String GetStringTest(String str,Scanner scanner){
        String s = scanner.nextLine();
        StrPrint(str,false);
        StrPrint(s,true);
        return s;
    }
    public int CheckInputContentTest(Scanner[] scanner){
        int i = 0;
        String str = GetStringTest("명령)",scanner[i++]);
        if(str.equals("등록")) return WiseInert(GetStringTest("명언 : ",scanner[i++]),GetStringTest("작가 : ",scanner[i++]));
        if (str.equals("종료")) return WiseExit();
        if (str.contains("목록")){
            PrintTitle();
            StrPrint(WiseList(str),true);
            return 0;
        };
        if (str.contains("삭제")) {
            StrPrint(WiseDelete(str),true);
            return 0;
        }
        if (str.contains("수정")){
            StrPrint(WiseChange(str,GetStringTest("명언 : ",scanner[i++]),GetStringTest("작가 : ",scanner[i++])),true);
            return 0;
        }
        if (str.equals("빌드")) return WiseBuild();
        return 0;
    }
}
