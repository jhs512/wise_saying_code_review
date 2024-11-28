package wisesaying_tdd.wisesaying.Service;

import java.util.ArrayList;
import java.util.List;

import wisesaying_tdd.WiseSaying;
import wisesaying_tdd.wisesaying.Repository.WiseSayingRepository;
import wisesaying_tdd.wisesaying.Util.FileUtil;

public class WiseSayingServiceImpl implements WiseSayingService{
    public static List<WiseSaying> servicelist = new ArrayList<>();
    public static WiseSayingRepository repository = new WiseSayingRepository();
    
    public int GetId(String cmd) {
        int id = FileUtil.GetId(cmd);

        return id;
    }

    public String AddWiseSaying(String cmd, int id, String wiseSaying, String authur) {
        String addResultMsg = "";
        addResultMsg = repository.AddWiseSaying(cmd, id, wiseSaying, authur);        
        
        return addResultMsg;
    }
    
    public List<WiseSaying> ShowWiseSayingList(String cmd) {;
        servicelist = repository.ShowWiseSaying(cmd);    
        return servicelist;
    }

    public String DeleteWiseSaying(String cmd,int id) {
        String delResultMsg = "";
        delResultMsg = repository.DeleteWiseSaying(cmd,id);

        return delResultMsg;
    }

    public String BuildWiseSaying(String cmd) {
        String buildResultMsg = repository.BuildWiseSaying(cmd);

        return buildResultMsg;
    }
}
