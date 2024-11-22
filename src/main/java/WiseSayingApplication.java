import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import infrastructure.wisesaying.WiseSayingRepository;
import infrastructure.wisesaying.WiseSayingRepositoryImpl;
import wisesaying.controller.WiseSayingController;
import wisesaying.service.WiseSayingService;
import wisesaying.service.WiseSayingServiceImpl;

public class WiseSayingApplication {
	public static void main(String[] args) {
		App app = new App();
		app.run();
	}
}

class App {
	private final ConcurrentHashMap<String, Object> container = init();
	private final WiseSayingController wiseSayingController
		= (WiseSayingController)container.get(WiseSayingController.class.getSimpleName());

	public void run() {
		wiseSayingController.run();
	}

	public ConcurrentHashMap<String, Object> init() {
		ConcurrentHashMap<String, Object> container = new ConcurrentHashMap<>();
		WiseSayingRepository wiseSayingRepository = new WiseSayingRepositoryImpl();
		WiseSayingService wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService, new Scanner(System.in));

		container.put(WiseSayingRepositoryImpl.class.getSimpleName(), wiseSayingRepository);
		container.put(WiseSayingServiceImpl.class.getSimpleName(), wiseSayingService);
		container.put(WiseSayingController.class.getSimpleName(), wiseSayingController);
		return container;
	}
}