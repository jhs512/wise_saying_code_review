package org.example;

import java.util.ArrayList;

public interface WiseService {
    int applyWise(String content, String author);

    Wise getWise(int id);

    ArrayList<Wise> getWises();

    void editWise(int id, String newContent, String newAuthor);

    boolean deleteWise(int id);

    boolean buildWise();
}
