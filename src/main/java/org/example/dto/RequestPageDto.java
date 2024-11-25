package org.example.dto;

import java.util.Optional;

public class RequestPageDto {
    public enum KeywordType{
        CONTENT, AUTHOR;

        public static KeywordType fromString(String keywordTypeText){
            if(keywordTypeText == null){
                return null;
            }
            try{
                return KeywordType.valueOf(keywordTypeText.toUpperCase());
            }catch (IllegalArgumentException e){
                return null;
            }
        }
    }

    private final Integer page;
    private final KeywordType keywordType;
    private final String keyword;

    public RequestPageDto(Integer page, String keywordTypeText, String keyword){
        this.page = page;
        this.keywordType = KeywordType.fromString(keywordTypeText);
        this.keyword = keyword;
    }

    public RequestPageDto(Integer page){
        this(page, null, null);
    }

    public RequestPageDto(String keywordTypeText, String keyword){
        this(null,keywordTypeText, keyword);
    }

    public RequestPageDto(){
        this(null);
    }

    public Optional<Integer> getPage(){
        return Optional.ofNullable(page);
    }

    public Optional<KeywordType> getKeywordType(){
        return Optional.ofNullable(keywordType);
    }

    public Optional<String> getKeyword(){
        return Optional.ofNullable(keyword);
    }
}
