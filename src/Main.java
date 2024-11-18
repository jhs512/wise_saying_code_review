import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

public class Main {


    public static ArrayList<String[]> wise = new ArrayList<String[]>();
    public static String url = ".\\db\\wiseSaying\\data.json";

    public static void StrPrint(String str, boolean IsLineBreak){
        if(IsLineBreak)
            System.out.println(str);
        else
            System.out.print(str);

    }
    public static String GetString(String str){
        StrPrint(str,false);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public static int GetId(String str){
        return Integer.parseInt(str.substring(str.indexOf("?id=")+4));
    }
    public static int CheckInputContent(String str){
        if(str.equals("등록")) return WiseInert();
        if (str.equals("종료")) return WiseExit();
        if (str.equals("목록")) return WiseList();
        if (str.contains("삭제")) return WiseDelete(str);
        if (str.contains("수정")) return WiseChange(str);
        if (str.equals("빌드")) return WiseBuild();
        return 0;
    }
    public static int WiseInert(){
        String[] content = new String[3];
        content[0] = "" + (wise.size()+1);
        content[1] = GetString("명언 : ");
        content[2] = GetString("작가 : ");
        wise.add(content);
        return 0;
    }
    public static int WiseExit(){
        return -1;
    }
    public static int WiseList(){
        StrPrint("번호 / 작가 / 명언",true);
        StrPrint("-----------------", true);
        for(String[] j : wise){
            if(j[1].equals("d_1241")) StrPrint(j[0] + "번 명언은 존재하지 않습니다.",true);
            else StrPrint(j[0] + " / " + j[1] + " / " + j[2],true);
        }
        return 0;
    }

    public static int WiseDelete(String str){
        int id =  GetId(str);
        String[] save = wise.get(id-1);
        if(save[1].equals("d_1241")) StrPrint(save[0] + "번 명언은 존재하지 않습니다.",true);
        save[1] ="d_1241";
        save[2] = "d_1241";
        return 0;
    }
    public static int WiseChange(String str){
        int id = GetId(str);
        String[] save = wise.get(id-1);
        if(save[1].equals("d_1241")){
            StrPrint("명언(기존) : 삭제된 명언입니다.",true);
            save[2] = GetString("명언 : ");
            StrPrint("작가(기존) : 삭제된 명언입니다.",true);
            save[1] = GetString("작가 : ");
            return 0;
        }
        StrPrint("명언(기존) : " + save[2],true);
        save[2] = GetString("명언 : ");
        StrPrint("작가(기존) : " + save[1],true);
        save[1] = GetString("작가 : ");
        return 0;

    }
    public static int WiseBuild(){
        JSONArray personArray = new JSONArray();
        for(String[] i : wise) {
            JSONObject json = new JSONObject();
            json.put("id", i[0]);
            json.put("author", i[1]);
            json.put("content", i[2]);

            personArray.add(json);
        }
        try {
            FileWriter file = new FileWriter(url);
            file.write(personArray.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {
        int chk = 0;
        while(chk==0){
            chk = CheckInputContent(GetString("명령) "));
        }
    }
}