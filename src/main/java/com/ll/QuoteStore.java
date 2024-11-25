package com.ll;

import com.ll.utils.FileManager;
import com.ll.utils.JsonQuote;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;


public class QuoteStore {


    private final FileManager fileManager;

    public QuoteStore(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void save(Quote quote) {
        quote.setId(fileManager.updateAndGetCurrentId(""));
        update(quote);
    }

    public void update(Quote quote) {
        fileManager.writeById("", quote.getId(), JsonQuote.quoteToJson(quote));
    }

    public void delete(Integer id) {
        fileManager.deleteById("", id);
    }

    public Quote find(int id) {
        String foundStr = fileManager.readById("", id);
        return (foundStr != null) ? JsonQuote.jsonToQuote(foundStr) : null;
    }

    public List<Quote> findAll() {
        return findAll(new HashMap<>());
    }

    public List<Quote> findAll(HashMap<String, String> queryMap) {
        List<Quote> list = new ArrayList<Quote>();
        File dir = new File(fileManager.getBasePath());

        String keywordType = queryMap.get("keywordType");
        String keyword = queryMap.get("keyword");

        for (String filename : dir.list()) {
            //filter1 로직
            String id = filename.substring(0, filename.lastIndexOf('.'));
            if (!filename.endsWith(".json") || !id.matches("\\d+"))
                continue;

            Quote q = JsonQuote.jsonToQuote(fileManager.readById("", Integer.parseInt(id)));

            //filter2
            if (keyword != null && keywordType != null && !String.valueOf(q.getByName(keywordType)).contains(keyword))
                continue;

            list.add(q);
        }

        list.sort((q1, q2) -> q2.getId() - q1.getId());
        return list;
    }

    public void saveBuild(List<Quote> quotes) {
        fileManager.writeBySubPath("data", JsonQuote.quotesToJsons(quotes));
    }

    public void clear() {
        File dir = new File(fileManager.getBasePath());

        for (String filename : dir.list()) {
            fileManager.delete(dir+"/"+filename);
        }
    }

}
