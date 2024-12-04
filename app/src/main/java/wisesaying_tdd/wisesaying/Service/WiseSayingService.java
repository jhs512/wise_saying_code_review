package wisesaying_tdd.wisesaying.Service;

import java.util.ArrayList;
import java.util.List;

import wisesaying_tdd.WiseSaying;
import wisesaying_tdd.wisesaying.Repository.WiseSayingRepository;

public interface WiseSayingService {
    List<WiseSaying> list = new ArrayList<>();
    WiseSayingRepository repository = new WiseSayingRepository();
    
    public static int GetId(String cmd) {
        return 0;
    }

    public static String AddWiseSaying(String cmd,int id, String wiseSaying, String authur) {
        return null;
    }
    
    public static List<WiseSaying> ShowWiseSayingList(String cmd) {
        return null;
    }

    public static String DeleteWiseSaying(String cmd,int id) {
        return null;
    }

    public static String BuildWiseSaying(String cmd) {
        return null;
    }
    
    public static List<WiseSaying> TargetRead(String cmd,int id) {
        return null;
    }

    public static String EditWiseSaying(String cmd,int targerId,String wiseSaying, String authur) {
        return null;
    }

}
