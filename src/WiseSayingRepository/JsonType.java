package WiseSayingRepository;

public class JsonType{
    String name;
    String stringContent;
    int intContent;
    int type;
    public JsonType(String name,String stringContent){
        type =0;
        this.name = name;
        this.stringContent = stringContent;
    }
    public JsonType(String name,int intContent){
        type=1;
        this.name = name;
        this.intContent =intContent;
    }
    public int getIntContent(){
        return intContent;
    }
    public String getName(){
        return name;
    }
    public String getStringContent(){
        return stringContent;
    }
    public int getType(){
        return type;
    }

}