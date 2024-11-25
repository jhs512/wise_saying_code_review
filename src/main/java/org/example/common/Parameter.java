package org.example.common;

public enum Parameter {
    KEYWORDTYPE("keywordType"),
    KEYWORD("keyword"),
    PAGE("page"),
    ID("id");

    private final String name;

    Parameter(String name){
        this.name =  name;
    }

    public String getName(){
        return name;
    }

    public static Parameter fromName(String name){
        for (Parameter parameter : Parameter.values()) {
            if (parameter.getName().equals(name)) {
                return parameter;
            }
        }
        throw new IllegalArgumentException("Unknown Parameter: " + name);
    }
}
