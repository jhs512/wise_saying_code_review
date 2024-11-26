package com.ll;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QuoteController {

    QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    public void saveQuote(Scanner sc) {
        System.out.print("명언: ");
        String sentence = sc.nextLine();
        System.out.print("작가: ");
        String author = sc.nextLine();

        Quote quote = new Quote();
        quote.setAuthor(author);
        quote.setContent(sentence);

        quoteService.saveQuote(quote);
    }

    public void deleteQuote(Integer id) {
        if (id == null) {
            System.out.println("쿼리를 입력하세요");
            return;
        }


        try {
            quoteService.deleteQuote(id);
        } catch (NoSuchElementException e) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }
        System.out.println(String.format("%d번 명언이 삭제되었습니다.", id));
    }

    public void updateQuote(Scanner sc, Integer id) {
        if (id == null) {
            System.out.println("쿼리를 입력하세요");
            return;
        }

        Quote foundQuote = quoteService.findQuote(id);

        if (foundQuote == null) {
            System.out.println("명언이 없습니다");
            return;
        }

        System.out.println(String.format("명언(기존) : %s", foundQuote.getContent()));
        System.out.print("명언: ");
        String sentence = sc.nextLine();

        System.out.println(String.format("작가(기존) : %s", foundQuote.getAuthor()));
        System.out.print("작가: ");
        String author = sc.nextLine();

        Quote updatedQuote = new Quote();
        updatedQuote.setId(id);
        updatedQuote.setAuthor(author);
        updatedQuote.setContent(sentence);

        try {
            quoteService.updateQuote(updatedQuote);
        } catch (NoSuchElementException e) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        System.out.println(String.format("%d번 명언이 수정되었습니다.", id));
    }

    public void quoteList(HashMap<String, String> queryMap) {

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        int page = (queryMap.containsKey("page") ? Integer.parseInt(queryMap.get("page")) : 1);
        int offset = 5;

        List<Quote> list = quoteService.findAllQuotes(queryMap);
        list = paginate(list, offset, page);
        int pages = computePages(list, offset);


        for (Quote quote : list) {
            System.out.println(
                    String.format(
                            "%d / %s / %s",
                            quote.getId(),
                            quote.getAuthor(),
                            quote.getContent())
            );
        }
        System.out.println("----------------------");
        System.out.println("페이지: %d / [%d]".formatted(page, pages));
    }

    public void buildQuotes() {
        try {
            quoteService.buildQuotes();
            System.out.println("data.json 파일의 내용이 갱신되었습니다.");
        } catch (Exception e) {
            throw e;
        }
    }

    private List<Quote> paginate(List<Quote> list, int offset, int page) {
        int pages = computePages(list, offset);

        if (page > pages || page <= 0) {
            throw new IndexOutOfBoundsException("페이지 번호 초과");
        }

        int begin = (page-1) * offset;
        int end = Math.min(begin+offset, list.size());
        return list.subList(begin, end);
    }

    private int computePages(List<Quote> list, int offset) {
        return (int) Math.ceil((double) list.size()/offset);
    }

}
