package com.ll.utils;

import com.ll.Quote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonQuote {

    public static void main(String[] args) {
        JsonQuote jq = new JsonQuote();
        Quote q1 = new Quote();
        q1.setId(1);
        q1.setContent("Asf s");
        q1.setAuthor("SDf");

        Quote q2 = new Quote();
        q2.setId(2);
        q2.setContent("rntrn");
        q2.setAuthor("aa");

        List<Quote> list = new ArrayList<>();
        Collections.addAll(list, q1, q2);
        System.out.println(jq.quotesToJsons(list));
    }

    public static String quoteToJson(Quote quote) {
        return quoteToJson(quote, 0);
    }

    public static String quoteToJson(Quote quote, int depth) {
        String tab = "\t".repeat(depth);
        String contentTab = "\t".repeat(depth+1);
        return String.format(tab + "{\n" +
                             contentTab + "\"id\": %d,\n" +
                             contentTab + "\"content\": \"%s\",\n" +
                             contentTab +"\"author\": \"%s\"\n" +
                             tab + "}",
                                quote.getId(),
                                quote.getContent(),
                                quote.getAuthor());
    }

    public static Quote jsonToQuote(String json) {
        String[] strings = json.substring(1, json.length()-1).split(",");
        Quote quote = new Quote();

        int id = Integer.parseInt(strings[0].split(":")[1].strip());

        String sentence = strings[1].split(":")[1].strip().strip();
        sentence = sentence.substring(1, sentence.length()-1);

        String author = strings[2].split(":")[1].strip().strip();
        author = author.substring(1, author.length()-1);

        quote.setId(id);
        quote.setContent(sentence);
        quote.setAuthor(author);

        return quote;
    }

    public static String quotesToJsons(List<Quote> quotes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Quote quote : quotes) {
            sb.append("\n");
            sb.append(quoteToJson(quote, 1));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n]");

        return sb.toString();
    }
}
