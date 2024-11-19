package Repository;

import java.util.ArrayList;

public class JsonArray {
    ArrayList<Json> jl = new ArrayList<>();
    String str = new String();
    public void put(Json json){
        jl.add(json);
    }

    private String GetGap(int n){
        return "    ".repeat(n);
    }
    private void creatString(String s,int num){
            str = str+GetGap(num)+s+"\n";
    }

    public String toString(){
        creatString("[",0);
        int JsonCount=0;
        for(Json j : jl){
            creatString("{",1);
            int JsonTypeCount=0;
            for(JsonType jt : j.getJsonContent()){
                if(jt.getType() == 0){
                    if(++JsonTypeCount == j.size())
                        creatString("\""+jt.getName()+"\": " + "\""+ jt.getStringContent()+"\"",2);
                    else
                        creatString("\""+jt.getName()+"\": " + "\""+ jt.getStringContent()+"\"" +",",2);

                } else{
                    if(++JsonTypeCount == j.size())
                        creatString("\""+jt.getName()+"\": " + jt.getIntContent(),2);
                    else
                        creatString("\""+jt.getName()+"\": " + jt.getIntContent() +",",2);
                }
            }
            if(++JsonCount == jl.size())
                creatString("}",1);
            else
                creatString("},",1);


        }
        creatString("]",0);

        return str;
    }


}
