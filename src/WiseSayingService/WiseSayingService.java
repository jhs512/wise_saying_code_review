package WiseSayingService;

import WiseSayingRepository.Json;
import WiseSayingRepository.JsonArray;


import java.util.ArrayList;



public class WiseSayingService {

    private ArrayList<String[]> wise = new ArrayList<>();

    public boolean chkId(int id) {
        if ((id >= wise.size()) || (id <0)) return true;
        return false;
    }


    public String wiseInert(String wiseWord, String name) {
        String[] content = new String[3];
        content[0] = "" + (wise.size() + 1);
        content[1] = wiseWord;
        content[2] = name;
        wise.add(content);
        return wise.size() + "번 명언 등록";
    }

    public int wiseExit() {
        return -1;
    }

    public String[] wiseList(String str) {
        WiseSayingPage page = new WiseSayingPage();
        String type = WiseSayingQuery.getType(str);
        String keyword = WiseSayingQuery.getKeyword(str);
        return page.getPageOutput(type,keyword,str,wise);
    }

    public String wiseDelete(String str) {
        int id = WiseSayingQuery.getId(str) - 1;
        if (chkId(id)) return (id + 1) + "번은 존재 하지않음";
        String[] save = wise.get(id);
        if (save[1].equals("d_1241")) return save[0] + "번은 존재 하지않음.";
        save[1] = "d_1241";
        save[2] = "d_1241";
        return "삭제완료";
    }

    public String[] wiseChange(String str, String wiseWord, String name) {
        int id = WiseSayingQuery.getId(str) - 1;
        String[] s = new String[1];
        if (chkId(id)) {
            s[0] = (id + 1) + "번은 존재 하지않음";
            return s;
        }
        String[] save = wise.get(id);
        String[] output = new String[4];
        output[0] = save[1].equals("d_1241") ? "명언(기존) : 삭제된 명언입니다.":"명언(기존) : " + save[2];
        output[1] = save[1].equals("d_1241") ?  "작가(기존) : 삭제된 명언입니다." : "작가(기존) : " + save[1];
        output[2] = "명언 : " + wiseWord;
        save[2] = wiseWord;
        output[3] = "작가 : " + name;
        save[1] = name;
        return output;

    }

    public String wiseBuild() {
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
    public String wiseLoad(){
        JsonArray personArray = new JsonArray();
        wise = personArray.loadTextToArrayList(File.File.load());
        return "정상 로드";
    }
}
