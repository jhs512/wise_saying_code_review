package baekgwa.controller;

import java.io.IOException;
import java.util.Map;

public interface WiseSayingController {
    void createDirectories() throws IOException;
    void register() throws IOException;

    @Deprecated(since = "13단계", forRemoval = false)
    void printAll() throws IOException;

    void delete(Long id) throws IOException;
    void modifyWiseSaying(Long id) throws IOException;
    void build() throws IOException;
    void search(Map<String, String> orders) throws IOException;
}
