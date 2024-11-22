package baekgwa.supporter;

import static baekgwa.global.database.TestDataSourceConfig.*;

import baekgwa.wisesaying.controller.WiseSayingControllerImpl;
import baekgwa.wisesaying.repository.WiseSayingRepository;
import baekgwa.wisesaying.repository.WiseSayingRepositoryImpl;
import baekgwa.wisesaying.service.WiseSayingService;
import baekgwa.wisesaying.service.WiseSayingServiceImpl;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class IntegrationTestSupporter {

    protected WiseSayingControllerImpl wiseSayingControllerImpl = null;
    protected WiseSayingService wiseSayingService = null;
    protected WiseSayingRepository wiseSayingRepository = null;
    protected BufferedReader bufferedReader = null;

    protected void initServiceAndRepository() {
        if (wiseSayingRepository == null) {
            wiseSayingRepository = new WiseSayingRepositoryImpl(DB_PATH, LAST_ID_FILE, BUILD_FILE);
        }
        if (wiseSayingService == null) {
            wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
        }
    }

    protected PrintStream initController(
            String input,
            OutputStream outputStream
    ) {
        this.bufferedReader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        wiseSayingControllerImpl = new WiseSayingControllerImpl(bufferedReader, wiseSayingService);

        PrintStream originOut = System.out;
        PrintStream changeStream = new PrintStream(outputStream);
        System.setOut(changeStream);
        return originOut;
    }

    protected void afterDetach(PrintStream originalOut) {
        System.setOut(originalOut);
        bufferedReader = null;
    }
}
