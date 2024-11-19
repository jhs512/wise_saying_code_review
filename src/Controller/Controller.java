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
    static  public String GetString(String str){
        StrPrint(str,false);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public int CheckInputContent(){
        String str = GetString("명령)");
        if(str.equals("등록")) return WiseInert();
        if (str.equals("종료")) return WiseExit();
        if (str.equals("목록")) return WiseList();
        if (str.contains("삭제")) return WiseDelete(str);
        if (str.contains("수정")) return WiseChange(str);
        if (str.equals("빌드")) return WiseBuild();
        return 0;
    }

}
