package com.ll;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class QuoteService {

    QuoteStore quoteStore;

    public QuoteService(QuoteStore quoteStore) {
        this.quoteStore = quoteStore;
    }

    public Quote findQuote(Integer id) {
        return quoteStore.find(id);
    }

    public List<Quote> findAllQuotes(HashMap<String, String> queryMap) {
        return quoteStore.findAll(queryMap);
    }

    public void saveQuote(Quote quote) {
        quoteStore.save(quote);
    }

    public void deleteQuote(int id) {
        if (quoteStore.find(id) == null) {
            throw new NoSuchElementException();
        };

        quoteStore.delete(id);

    }

    public void updateQuote(Quote quote) {
        int id = quote.getId();

        if (quoteStore.find(id) == null) {
            throw new NoSuchElementException();
        }

        quoteStore.update(quote);
    }

    public void buildQuotes() {
        quoteStore.saveBuild(quoteStore.findAll());
    }

}
