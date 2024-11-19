package Service;

import Controller.Controller;
import Repository.Json;
import Repository.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static Controller.Controller.GetString;
import static Controller.Controller.StrPrint;

public class Service {

    public ArrayList<String[]> wise = new ArrayList<String[]>();
    public String url = ".\\db\\wiseSaying\\data.json";

    public int GetId(String str){
        return Integer.parseInt(str.substring(str.indexOf("?id=")+4));
    }
    public int WiseInert(){
        String[] content = new String[3];
        content[0] = "" + (wise.size()+1);
        content[1] = GetString("명언 : ");
        content[2] = GetString("작가 : ");
        wise.add(content);
        return 0;
    }
    public int WiseExit(){
        return -1;
    }
    public int WiseList(){
        StrPrint("번호 / 작가 / 명언",true);
        StrPrint("-----------------", true);
        for(String[] j : wise){
            if(j[1].equals("d_1241")) StrPrint(j[0] + "번 명언은 존재하지 않습니다.",true);
            else StrPrint(j[0] + " / " + j[1] + " / " + j[2],true);
        }
        return 0;
    }

    public int WiseDelete(String str){
        int id =  GetId(str);
        String[] save = wise.get(id-1);
        if(save[1].equals("d_1241")) StrPrint(save[0] + "번 명언은 존재하지 않습니다.",true);
        save[1] ="d_1241";
        save[2] = "d_1241";
        return 0;
    }
    public int WiseChange(String str){
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
    public int WiseBuild(){
        JsonArray personArray = new JsonArray();
        for(String[] i : wise) {
            Json json = new Json();
            json.put("id", Integer.parseInt(i[0]));
            json.put("author", i[1]);
            json.put("content", i[2]);

            personArray.put(json);
        }
        try {
            FileWriter file = new FileWriter(url);
            file.write(personArray.toString());
            file.flush();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
