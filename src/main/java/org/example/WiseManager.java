package org.example;

import java.util.ArrayList;
import java.util.Optional;

public class WiseManager {
    private ArrayList<Wise> wises = new ArrayList<>();
    private int index = 1;

    public void applyWise(String wise, String author) {
        wises.add(new Wise(index, author, wise));
        System.out.println(index++ + "번 명언이 등록되었습니다.");
    }

    public void printWises() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("---------------");

        wises.forEach(wise -> System.out.println(wise));
    }

    public boolean deleteWise(int id) {
        return wises.removeIf(wise -> wise.index == id);
    }

    public Wise findWise(int id) {
        Optional<Wise> result = wises.stream().filter(wise -> wise.index == id).findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
}
