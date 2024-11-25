package WiseSayingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page {
    protected int page;
    protected int totalPage;
    protected List<String> output = new ArrayList<>();

    public Page() {
        page = 1;
        totalPage = 1;
        output = new ArrayList<>();
    }

    public void setPage(String str,String query) {
        String s = Query.getQueryContent(str, query);
        if (Objects.equals(s, "")) page = 1;
        else page = Integer.parseInt(s);
    }

    public void setTotalPage(int n) {
        totalPage = calPage(n);
    }

    public int calPage(int num) {
        float n = num;
        return (int) Math.max(Math.ceil(n / 5), 1);

    }

    public void checkPage() {
        page = Math.min(totalPage, page);
    }



}
