package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Wise> getWises(int page) {
        return repository.getWises(page);
    }

    @Override
    public List<Wise> getWises(String keywordType, String keyword, int page) {
        return repository.getWises(keywordType, keyword, page);
    }

    @Override
    public int getWiseSize() {
        return repository.getWiseSize();
    }

    @Override
    public int getWiseSize(String keywordType, String keyword) {
        return repository.getWiseSize(keywordType, keyword);
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
