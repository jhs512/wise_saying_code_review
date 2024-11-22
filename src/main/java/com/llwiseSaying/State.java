package com.llwiseSaying;

public enum State {

    EXIT (1),
    PROCESS(2);

    private final int code;

    State(int code) {
        this.code = code;
    }

    // Getter 메서드
    public int getCode() {
        return code;
    }
}
