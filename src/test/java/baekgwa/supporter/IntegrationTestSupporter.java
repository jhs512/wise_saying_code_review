package baekgwa.supporter;

import baekgwa.controller.WiseSayingController;
import baekgwa.repository.WiseSayingRepository;
import baekgwa.repository.WiseSayingRepositoryImpl;
import baekgwa.service.WiseSayingService;
import baekgwa.service.WiseSayingServiceImpl;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class IntegrationTestSupporter {

    protected WiseSayingController wiseSayingController = null;
    protected WiseSayingService wiseSayingService = null;
    protected WiseSayingRepository wiseSayingRepository = null;
    protected BufferedReader bufferedReader = null;

    protected void initServiceAndRepository() {
        if(wiseSayingRepository == null) wiseSayingRepository = new WiseSayingRepositoryImpl();
        if(wiseSayingService == null) wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
    }

    protected PrintStream initController(
            String input,
            OutputStream outputStream
    ) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(input.getBytes())));
        wiseSayingController = new WiseSayingController(bufferedReader, wiseSayingService);

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
