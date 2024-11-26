package org.example.mvc.controller;

import java.io.IOException;

public interface WiseSayingController {

    void registerWiseSaying() throws IOException;

    void deleteWiseSaying(String command);

    void deleteAllWiseSaying();

    void updateWiseSaying(String command) throws IOException;

    void search(String command) throws IOException;

    void build();

}
