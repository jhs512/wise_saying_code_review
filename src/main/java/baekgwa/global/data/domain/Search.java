package baekgwa.global.data.domain;

public class Search {
    public static final String KEYWORD_TYPE = "keywordType";
    public static final String KEYWORD = "keyword";
    private final String keywordType;
    private final String keyword;

    public Search(String keywordType, String keyword) {
        this.keywordType = keywordType;
        this.keyword = keyword;
    }

    public static Search empty(){
        return new Search(null, null);
    }

    public boolean isEmpty() {
        return keywordType == null || keyword == null;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public String getKeyword() {
        return keyword;
    }
}
