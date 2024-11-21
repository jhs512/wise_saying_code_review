package Service;
import Repository.Json;
import Repository.JsonArray;
import Repository.Util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Service {

    public ArrayList<String[]> wise = new ArrayList<>();
    public String[] SeparateString(String str){
        String[] a = str.split("\\?");
        if(a.length <2) return null;
        return a[1].split("&");
    }
    public boolean isDigit(String str){
        for(char c : str.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public String GetQueryContent(String strOri,String strSearch){
        String[] strs = SeparateString(strOri);
        if(strs == null) return "";
        for(String i : strs){
            if(i.contains(strSearch)){
                return i.split("=")[1];
            };
        }
        return "";
    }
    public int GetId(String str) {
        String s = GetQueryContent(str,"id");
        if(s == "" || !isDigit(s)) return -1;
        return Integer.parseInt(GetQueryContent(str,"id"));
    }


    public String GetKeyword(String str) {
        return GetQueryContent(str,"keyword");
    }

    public String GetType(String str) {
        return GetQueryContent(str,"keywordType");
    }

    public boolean ChkId(int id) {
        if ((id >= wise.size()) || (id <0)) return true;
        return false;
    }
    public int GetPage(String str) {
        String s = GetQueryContent(str,"page");
        if(s == "") return 1;
        return Integer.parseInt(s);

    }
    public int GetPage(int num) {
        float n = num;
        return (int) Math.ceil(n/5);


    }

    public int WiseInert(String wiseWord, String name) {
        String[] content = new String[3];
        content[0] = "" + (wise.size() + 1);
        content[1] = wiseWord;
        content[2] = name;
        wise.add(content);
        return 0;
    }

    public int WiseExit() {
        return -1;
    }

    public String[] WiseList(String str) {
        String[] output = new String[wise.size()+2];
        int page = GetPage(str);
        int totalPage;
        String type = GetType(str);
        String keyword = GetKeyword(str);

        int i = 0;
        if ((type == "") || (keyword == "")) {
            totalPage = Math.max(GetPage(wise.size()),1);
            page = Math.min(totalPage,page);
            List<String[]> arr =  wise.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).skip((page-1)*5).limit((page)*5).toList();
            for (String[] j : arr) {
                if (j[1].equals("d_1241")) output[i] = (j[0] + "번 명언은 존재하지 않습니다.");
                else output[i] = j[0] + " / " + j[1] + " / " + j[2];
                i++;
            }
        } else {
            List<String[]> arr1 = new ArrayList<>();
            if (type.equals("content")) arr1 = wise.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[2].contains(keyword)).toList();
            else if (type.equals("author"))  arr1 =  wise.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[1].contains(keyword)).toList();
            totalPage = Math.max(GetPage(arr1.size()),1);
            page = Math.min(totalPage,page);
            List<String[]> arr =  arr1.stream().skip((page-1)*5).limit((page)*5).toList();
            for (String[] j : arr) {
                    output[i] = j[0] + " / " + j[1] + " / " + j[2];
                    i++;
                }
            }
        output[i++] = "--------------";
        output[i] = "페이지 : [" +page +"] / "+totalPage;
        ;

        return output;
    }

    public String WiseDelete(String str) {
        int id = GetId(str) - 1;
        if (ChkId(id)) return (id + 1) + "번은 존재 하지않음";
        String[] save = wise.get(id);
        if (save[1].equals("d_1241")) return save[0] + "번 명언은 존재하지 않습니다.";
        save[1] = "d_1241";
        save[2] = "d_1241";
        return "삭제완료";
    }

    public String[] WiseChange(String str, String wiseWord, String name) {
        int id = GetId(str) - 1;
        String[] s = new String[1];
        if (ChkId(id)) {
            s[0] = (id + 1) + "번은 존재 하지않음";
            return s;
        }
        String[] save = wise.get(id);
        String[] output = new String[4];
        if (save[1].equals("d_1241")) {
            output[0] = "명언(기존) : 삭제된 명언입니다.";
            save[2] = wiseWord;
            output[1] = "작가(기존) : 삭제된 명언입니다.";
            save[1] = name;
        } else {
            output[0] = "명언(기존) : " + save[2];
            save[2] = wiseWord;
            output[1] = "작가(기존) : " + save[1];
            save[1] = name;
        }
        output[2] = "명언 : " + wiseWord;
        save[2] = wiseWord;
        output[3] = "작가 : " + name;
        save[1] = name;
        return output;

    }

    public int WiseBuild() {
        JsonArray personArray = new JsonArray();
        for (String[] i : wise) {
            Json json = new Json();
            json.put("id", Integer.parseInt(i[0]));
            json.put("author", i[1]);
            json.put("content", i[2]);
            personArray.put(json);
        }
        Util.File.save(personArray);
        return 0;
    }
    public int WiseLoad(){
        return 0;
    }
}
