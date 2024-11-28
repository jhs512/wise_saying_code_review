package wisesaying_tdd;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in,"EUC-KR");
        App app = new App(sc);
        app.run();
    }
    
}
