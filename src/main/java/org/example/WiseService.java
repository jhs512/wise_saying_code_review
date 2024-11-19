package org.example;

import java.io.IOException;
import java.util.ArrayList;

public interface WiseService {
    int applyWise(String content, String author) throws IOException;

    Wise getWise(int id);

    ArrayList<Wise> getWises();

    void editWise(int id, String newContent, String newAuthor) throws IOException;

    boolean deleteWise(int id);

    boolean buildWise();
}
