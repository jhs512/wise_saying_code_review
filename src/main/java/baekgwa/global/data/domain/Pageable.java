package baekgwa.global.data.domain;

public class Pageable {

    private final int page;
    private final int size;
    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_SIZE = 5;
    public static final String PAGE = "page";
    public static final String SIZE = "size";

    public Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Pageable(int page) {
        this(page, DEFAULT_SIZE);
    }

    public Pageable() {
        this(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
