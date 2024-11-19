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
            if (IsLineBreak)
                System.out.println(str);
            else
                System.out.print(str);
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
            StrPrint(WiseList(),true);
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
        return 0;
    }


}
