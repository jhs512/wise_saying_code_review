package EnumCollect;

public enum wiseSayingEnum {
    KEYWORD("keyword"),
    KEYWORD_TYPE("keywordType"),
    PAGE("page"),
    ID("id");
    private final String query;
    wiseSayingEnum(String query){
        this.query = query;
    }
    public String getString(){
        return query;
    }
}
