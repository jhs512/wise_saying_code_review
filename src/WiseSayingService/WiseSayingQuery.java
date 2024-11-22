package WiseSayingService;

public class WiseSayingQuery {

    public static String GetQueryContent(String strOri,String strSearch){
        String[] strs = SeparateString(strOri);
        if(strs == null) return "";
        for(String i : strs){
            if(i.contains(strSearch)){
                return i.split("=")[1];
            };
        }
        return "";
    }
    public static String[] SeparateString(String str){
        String[] a = str.split("\\?");
        if(a.length <2) return null;
        return a[1].split("&");
    }

    public static boolean isDigit(String str){
        for(char c : str.toCharArray()){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public static String GetKeyword(String str) {return GetQueryContent(str,"keyword");}

    public static String GetType(String str) {
        return GetQueryContent(str,"keywordType");
    }

    public static int GetId(String str) {
        String s = GetQueryContent(str,"id");
        if(s == "" || !isDigit(s)) return -1;
        return Integer.parseInt(GetQueryContent(str,"id"));
    }



}
