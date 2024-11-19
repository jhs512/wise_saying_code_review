package Repository;

import java.util.ArrayList;

public class Json {

    ArrayList<JsonType> jt = new ArrayList<>();
    public void put(String str,String content){
        jt.add(new JsonType(str,content));
    }
    public void put(String str,int content){
        jt.add(new JsonType(str,content));
    }
    public ArrayList<JsonType> getJsonContent(){
        return jt;
    }
    public int size(){
        return jt.size();
    }
}