package baekgwa.supporter;

import baekgwa.controller.WiseSayingController;
import baekgwa.dto.RequestDto;
import baekgwa.dto.ResponseDto;
import baekgwa.dto.ResponseDto.FindList;
import baekgwa.entity.WiseSaying;
import baekgwa.service.mock.MockWiseSayingService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class ControllerTestSupporter {

    protected final WiseSaying baseWiseSaying = new WiseSaying(1L, "content", "author");
    protected WiseSayingController wiseSayingController;
    private BufferedReader bufferedReader = null;

    protected void initInput(String input) {
        bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes())));
        initController();
    }

    protected PrintStream initOutput(OutputStream outputStream) {
        PrintStream originalOut = System.out;
        PrintStream changeOut = new PrintStream(outputStream);
        System.setOut(changeOut);
        return originalOut;
    }

    protected void afterDetach(PrintStream originalOut) {
        System.setOut(originalOut);
        bufferedReader = null;
    }

    protected void initController() {
        this.wiseSayingController = new WiseSayingController(
                bufferedReader, new MockWiseSayingService() {
            @Override
            public Long registerWiseSaying(RequestDto.Register data) {
                return 1L;
            }

            @Override
            public List<ResponseDto.FindList> findAllWiseSaying() {
                return List.of(new ResponseDto.FindList(baseWiseSaying.getAuthor(), baseWiseSaying.getContent(), baseWiseSaying.getId()));
            }

            @Override
            public Boolean deleteWiseSaying(Long id) {
                if(id.equals(1L)) return true;
                return false;
            }

            @Override
            public Optional<ResponseDto.FindSayingInfo> findWiseSayingInfo(Long id) {
                if(id.equals(1L)) return Optional.of(new ResponseDto.FindSayingInfo(
                        baseWiseSaying.getAuthor(), baseWiseSaying.getContent(), baseWiseSaying.getId()));
                return Optional.empty();
            }

            @Override
            public void modifyWiseSaying(ResponseDto.ModifyInfo modifyInfo) {}

            @Override
            public void createDirectories() throws IOException {}

            @Override
            public void build() {}

            @Override
            public List<FindList> search(Map<String, String> orders) {
                return List.of();
            }
        });
    }
}
