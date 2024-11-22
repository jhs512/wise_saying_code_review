package com.ll.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.ll.util.ParamUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class ParamUtilTest {

    @Test
    void 파라미터유틸_테스트(){
        Map<String, String> parse = parse("목록?keywordType=author&keyword=작자");
        Assertions.assertEquals(parse.get("keywordType"), "author");
        Assertions.assertEquals(parse.get("keyword"), "작자");
    }
}