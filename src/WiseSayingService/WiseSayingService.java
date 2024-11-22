package WiseSayingService;

import WiseSayingRepository.Json;
import WiseSayingRepository.JsonArray;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class WiseSayingService {

    private ArrayList<String[]> wise = new ArrayList<>();

    public boolean ChkId(int id) {
        if ((id >= wise.size()) || (id <0)) return true;
        return false;
    }


    public String WiseInert(String wiseWord, String name) {
        String[] content = new String[3];
        content[0] = "" + (wise.size() + 1);
        content[1] = wiseWord;
        content[2] = name;
        wise.add(content);
        return wise.size() + "번 명언 등록";
    }

    public int WiseExit() {
        return -1;
    }

    public String[] WiseList(String str) {
        WiseSayingPage page = new WiseSayingPage();
        page.SetPage(str);
        page.SetTotalPage(wise.size());
        String type = WiseSayingQuery.GetType(str);
        String keyword = WiseSayingQuery.GetKeyword(str);
        return page.GetPageOutput(type,keyword,wise);
    }

    public String WiseDelete(String str) {
        int id = WiseSayingQuery.GetId(str) - 1;
        if (ChkId(id)) return (id + 1) + "번은 존재 하지않음";
        String[] save = wise.get(id);
        if (save[1].equals("d_1241")) return save[0] + "번은 존재 하지않음.";
        save[1] = "d_1241";
        save[2] = "d_1241";
        return "삭제완료";
    }

    public String[] WiseChange(String str, String wiseWord, String name) {
        int id = WiseSayingQuery.GetId(str) - 1;
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

    public String WiseBuild() {
        JsonArray personArray = new JsonArray();
        for (String[] i : wise) {
            Json json = new Json();
            json.put("id", Integer.parseInt(i[0]));
            json.put("author", i[1]);
            json.put("content", i[2]);
            personArray.put(json);
        }
        File.File.save(personArray);
        return "빌드완료";
    }
    public String WiseLoad(){
        JsonArray personArray = new JsonArray();
        wise = personArray.LoadTextToArrayList(File.File.load());
        return "정상 로드";
    }
}
