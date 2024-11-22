package WiseSayingService;

public class WiseSayingQuery {

    public static String getQueryContent(String strOri, String strSearch){
        String[] strs = separateString(strOri);
        if(strs == null) return "";
        for(String i : strs){
            if(i.contains(strSearch)){
                return i.split("=")[1];
            };
        }
        return "";
    }
    public static String[] separateString(String str){
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

    public static String getKeyword(String str) {return getQueryContent(str,"keyword");}

    public static String getType(String str) {
        return getQueryContent(str,"keywordType");
    }

    public static int getId(String str) {
        String s = getQueryContent(str,"id");
        if(s == "" || !isDigit(s)) return -1;
        return Integer.parseInt(getQueryContent(str,"id"));
    }



}
