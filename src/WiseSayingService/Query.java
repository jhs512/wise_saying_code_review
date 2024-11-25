package WiseSayingService;

public class Query {

    public static String calQueryContent(String strOri, String type){
        String[] strs = separateString(strOri);
        if(strs == null) return "";
        for(String i : strs){
            if(i.contains(type)){
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
    public static String getQueryContent(String strOri, String type){
        return calQueryContent(strOri,type);
    }
    public static int getQueryContentInteger(String str,String type){
        String s = calQueryContent(str,type);
        if(s == "" || !isDigit(s)) return -1;
        return Integer.parseInt(calQueryContent(str,type));
    }



}
