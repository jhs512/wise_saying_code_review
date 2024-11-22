package WiseSayingService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class WiseSayingPage {
    private int page;
    private int totalPage;
    private List<String> output = new ArrayList<>();

    public WiseSayingPage() {
        page = 1;
        totalPage = 1;
        output = new ArrayList<>();
    }

    public void SetPage(String str) {
        String s = WiseSayingQuery.GetQueryContent(str, "page");
        if (Objects.equals(s, "")) page = 1;
        else page = Integer.parseInt(s);
    }

    public void SetTotalPage(int n) {
        totalPage = CalPage(n);
    }

    public int CalPage(int num) {
        float n = num;
        return (int) Math.max(Math.ceil(n / 5), 1);

    }

    public void CheckPage() {
        page = Math.min(totalPage, page);
    }

    public List<String[]> CalNullType(List<String[]> list,String str) {
        SetPage(str);
        SetTotalPage(list.size());
        CheckPage();
        return list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).skip((page - 1) * 5L).limit((page) * 5L).toList();
    }

    public List<String[]> CalNotNullType(String type, String keyword,String str, List<String[]> list) {
        if (type.equals("content"))
            list = list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[2].contains(keyword)).toList();
        else if (type.equals("author"))
            list = list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[1].contains(keyword)).toList();
        SetPage(str);
        SetTotalPage(list.size());
        CheckPage();
        return list.stream().skip((page - 1) * 5).limit((page) * 5).toList();

    }

    public String[] GetPageOutput(String type, String keyword,String str, List<String[]> list) {
        if ((Objects.equals(type, "")) || (Objects.equals(keyword, ""))) {
            list = CalNullType(list,str);
        } else {
            list = CalNotNullType(type, keyword,str, list);
        }

        for (String[] i : list) {
            if (i[1].equals("d_1241")) output.add((i[0] + "번 명언은 존재하지 않습니다."));
            else output.add(i[0] + " / " + i[1] + " / " + i[2]);
        }
        output.add("--------------");
        output.add("페이지 : [" +page +"] / "+totalPage);
        return ToStringArr(output);
    }

    public String[] ToStringArr(List<String> output) {
        String[] arr = new String[output.size()];
        for(int i = 0; i< output.size(); i++){
            arr[i] = output.get(i);
        }
        return arr;
    }


}
