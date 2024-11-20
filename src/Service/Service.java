package Service;

import Repository.Json;
import Repository.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Service {

    public ArrayList<String[]> wise = new ArrayList<>();
    public String url = ".\\db\\wiseSaying\\data.json";

    public int GetId(String str){
        return Integer.parseInt(str.substring(str.indexOf("?id=")+4));
    }
    public boolean ChkId(int id){
        if(id > wise.size()) return true;
        return false;
    }
    public int WiseInert(String wiseWord,String name){
        String[] content = new String[3];
        content[0] = "" + (wise.size()+1);
        content[1] = wiseWord;
        content[2] = name;
        wise.add(content);
        return 0;
    }
    public int WiseExit(){
        return -1;
    }
    public String[] WiseList(){
        String[] output = new String[wise.size()];
        int i = 0;
        for(String[] j : wise){
            if(j[1].equals("d_1241")) output[i] = (j[0] + "번 명언은 존재하지 않습니다.");
            else output[i] =  j[0] + " / " + j[1] + " / " + j[2];
            i++;
        }
        return output;
    }

    public String WiseDelete(String str){
        int id =  GetId(str)-1;
        if(ChkId(id)) return (id+1) + "번은 존재 하지않음";
        String[] save = wise.get(id);
        if(save[1].equals("d_1241")) return save[0] + "번 명언은 존재하지 않습니다.";
        save[1] ="d_1241";
        save[2] = "d_1241";
        return "삭제완료";
    }
    public String[] WiseChange(String str,String wiseWord,String name){
        int id = GetId(str)-1;
        String[] s = new String[1];
        if(ChkId(id)) {
            s[0] = (id+1) + "번은 존재 하지않음";
            return s;
        }
        String[] save = wise.get(id);
        String[] output = new String[4];
        if(save[1].equals("d_1241")){
            output[0] = "명언(기존) : 삭제된 명언입니다.";
            save[2] = wiseWord;
            output[1] ="작가(기존) : 삭제된 명언입니다.";
            save[1] = name;
        }
        else{
            output[0] = "명언(기존) : "+save[2];
            save[2] = wiseWord;
            output[1] ="작가(기존) : "+save[1];
            save[1] = name;
        }
        output[2] = "명언 : " + wiseWord;
        save[2] =wiseWord;
        output[3] ="작가 : "+name;
        save[1] = name;
        return output;

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
