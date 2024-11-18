//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public enum ChkType{

        INSERT,EXIT,LIST,DELETE,CHANGE,NOTHING;

    }
    public void StrPrint(String str,boolean IsLineBreak){
        if(IsLineBreak)
            System.out.println(str);
        else
            System.out.print(str);

    }
    public String GetString(String str){
        StrPrint(str,false);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public ChkType CheckInputContent(String str){
        if(str.equals("등록")) return ChkType.INSERT;
        else if (str.equals("종료")) return ChkType.EXIT;
        else if (str.equals("목록")) return ChkType.LIST;
        else if (str.contains("삭제")) return ChkType.DELETE;
        else if (str.contains("수정")) return ChkType.CHANGE;
        return ChkType.NOTHING;
    }
    public static void main(String[] args) {

    }
}