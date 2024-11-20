package org.example.view;

import org.example.dto.WiseSayingDTO;

import java.util.Scanner;

public class TerminalView {
    Scanner scanner = new Scanner(System.in);

    public void add(WiseSayingDTO model) {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        model.setContent(content);
        model.setAuthor(author);
    }

    public void update(WiseSayingDTO model) {
        System.out.println("명언(기존) : " + model.getContent());
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.println("작가(기존) : " + model.getAuthor());
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        model.setContent(content);
        model.setAuthor(author);
    }
}