package org.example;

import java.io.IOException;
import java.util.ArrayList;

public class WiseServiceImpl implements WiseService {
    private WiseRepository repository;

    public WiseServiceImpl(WiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public int applyWise(String content, String author) throws IOException {
        return repository.applyWise(content, author);
    }

    @Override
    public Wise getWise(int id) {
        return repository.findWise(id);
    }

    @Override
    public ArrayList<Wise> getWises() {
        return repository.getWises();
    }

    @Override
    public void editWise(int id, String newContent, String newAuthor) throws IOException {
        repository.editWise(id, newContent, newAuthor);
    }

    @Override
    public boolean deleteWise(int id) {
        return repository.deleteWise(id);
    }

    @Override
    public boolean buildWise() {
        return repository.saveWises();
    }
}
