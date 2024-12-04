package wisesaying_tdd.wisesaying.Service;

import java.util.ArrayList;
import java.util.List;

import wisesaying_tdd.WiseSaying;
import wisesaying_tdd.wisesaying.Repository.WiseSayingRepository;
import wisesaying_tdd.wisesaying.Util.FileUtil;

public class WiseSayingServiceImpl implements WiseSayingService{
    public static List<WiseSaying> servicelist = new ArrayList<>();
    public static WiseSayingRepository repository = new WiseSayingRepository();
    String msg = "";
    
    public int GetId(String cmd) {
        int id = FileUtil.GetId(cmd);

        return id;
    }

    public String AddWiseSaying(String cmd, int id, String wiseSaying, String authur) {
        msg = repository.AddWiseSaying(cmd, id, wiseSaying, authur);        
        
        return msg;
    }
    
    public List<WiseSaying> ShowWiseSayingList(String cmd) {;
        servicelist = repository.ShowWiseSaying(cmd);    
        return servicelist;
    }

    public String DeleteWiseSaying(String cmd,int id) {
        msg = repository.DeleteWiseSaying(cmd,id);

        return msg;
    }

    public String BuildWiseSaying(String cmd) {
        msg = repository.BuildWiseSaying(cmd);

        return msg;
    }

    public List<WiseSaying> TargetRead(String cmd,int id) {
        servicelist = repository.ReadTargetFile(cmd, id);

        return servicelist;
    }
    

    public String EditWiseSaying(String cmd,int targerId,String wiseSaying, String authur) {
        msg = repository.EditWiseSaying(cmd,targerId,wiseSaying,authur);

        return msg;
    }
}
