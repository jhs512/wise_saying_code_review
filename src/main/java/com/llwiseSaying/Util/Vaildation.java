package com.llwiseSaying.Util;

import com.llwiseSaying.WiseSaying;

import java.util.Map;

public class Vaildation {

    public void vaildationId(int id, Map<Integer, WiseSaying> wiseSayings) {
        if(!wiseSayings.containsKey(id)) {
            throw new IllegalArgumentException(id+"번 명언은 존재하지 않습니다.");
        }
    }

    public void vaildationKeyword(String input,String delimiter) {
        String [] splitData=input.split(delimiter);

        if(splitData.length !=2) {throw new IllegalArgumentException("명령어 형식을 맞춰주세요.");}

    }

    public void vaildationKeyword2(String expected,String actual) {
        if(!expected.equals(actual)) {throw  new IllegalArgumentException("명령어 형식을 맞춰주세요");}
    }


}
