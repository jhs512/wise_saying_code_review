import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import Controller.Controller;


public class Main {

    public static void main(String[] args) {
        int chk = 0;
        Controller c = new Controller();
        while(chk==0){
            chk = c.CheckInputContent();
        }
    }
}