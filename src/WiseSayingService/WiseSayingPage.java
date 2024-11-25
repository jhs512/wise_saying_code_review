package WiseSayingService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static EnumCollect.wiseSayingEnum.*;

public class WiseSayingPage extends  Page{
    public List<String[]> calNullType(List<String[]> list, String str) {
        setPage(str,PAGE.getString());
        setTotalPage(list.size());
        checkPage();
        return list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).skip((page - 1) * 5L).limit((page) * 5L).toList();
    }

    public List<String[]> calNotNullType(String type, String keyword, String str, List<String[]> list) {
        if (type.equals("content"))
            list = list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[2].contains(keyword)).toList();
        else if (type.equals("author"))
            list = list.stream().sorted(Comparator.comparing((String[] arrs) -> arrs[0]).reversed()).filter(arr -> arr[1].contains(keyword)).toList();
        setPage(str,PAGE.getString());
        setTotalPage(list.size());
        checkPage();
        return list.stream().skip((page - 1) * 5).limit((page) * 5).toList();

    }

    public String[] getPageOutput(String type, String keyword, String str, List<String[]> list) {
        if ((Objects.equals(type, "")) || (Objects.equals(keyword, ""))) {
            list = calNullType(list,str);
        } else {
            list = calNotNullType(type, keyword,str, list);
        }

        for (String[] i : list) {
            if (i[1].equals("d_1241")) output.add((i[0] + "번 명언은 존재하지 않습니다."));
            else output.add(i[0] + " / " + i[1] + " / " + i[2]);
        }
        output.add("--------------");
        output.add("페이지 : [" +page +"] / "+totalPage);
        return toStringArr(output);
    }

    public String[] toStringArr(List<String> output) {
        String[] arr = new String[output.size()];
        for(int i = 0; i< output.size(); i++){
            arr[i] = output.get(i);
        }
        return arr;
    }
}
