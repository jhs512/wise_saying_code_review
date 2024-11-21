import Controller.Controller;
import Service.Service;


public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Controller c = new Controller(service);
        c.run();

    }
}