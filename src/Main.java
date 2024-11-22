import WiseSayingController.WiseSayingController;
import WiseSayingService.WiseSayingService;

public class Main {
    public static void main(String[] args) {
        WiseSayingService service = new WiseSayingService();
        WiseSayingController c = new WiseSayingController(service);
        c.run();

    }
}