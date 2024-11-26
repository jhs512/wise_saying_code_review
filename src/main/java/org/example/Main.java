package org.example;

public class Main {
    public static void main(String[] args) {
        WiseController controller = new WiseController(new WiseServiceImpl(new WiseRepository()));

        controller.run();
    }
}