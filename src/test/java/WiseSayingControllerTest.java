import org.example.WiseSaying;
import org.example.WiseSayingController;
import org.example.WiseSayingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class WiseSayingControllerTest {
    private WiseSayingController controller;
    private WiseSayingService service;
    private BufferedReader br;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(WiseSayingService.class);
        br = mock(BufferedReader.class);

        controller = new WiseSayingController(br);
        controller.service = service;
    }

    private List<WiseSaying> prepareMockData() {
        return List.of(
                new WiseSaying(10, "명언 10", "작자미상 10"),
                new WiseSaying(9, "명언 9", "작자미상 9"),
                new WiseSaying(8, "명언 8", "작자미상 8"),
                new WiseSaying(7, "명언 7", "작자미상 7"),
                new WiseSaying(6, "명언 6", "작자미상 6")
        );
    }

    @Test
    public void registerTest() throws IOException {
        //Given
        List<WiseSaying> mockData = prepareMockData();
        when(br.readLine());
        when(service.registerWiseSaying("현재를 사랑하라.","작자미상")).thenReturn(1);

        //When
        controller.registerWiseSaying();

        //Then
        verify(service).registerWiseSaying("현재를 사랑하라.","작자미상");
        verify(br,times(2)).readLine();
    }

    @Test
    void listTest() throws IOException {
        List<WiseSaying> mockData = Arrays.asList(
                new WiseSaying(10, "명언 10", "작자미상 10"),
                new WiseSaying(9, "명언 9", "작자미상 9"),
                new WiseSaying(8, "명언 8", "작자미상 8"),
                new WiseSaying(7, "명언 7", "작자미상 7"),
                new WiseSaying(6, "명언 6", "작자미상 6")
        );
        when(br.readLine()).thenReturn("1"); // 페이지 번호 입력
        when(service.listWiseSayings(1)).thenReturn(mockData);
        when(service.getTotalPages()).thenReturn(2);

        // When: 테스트 메서드 실행
        controller.listWiseSayings();

        // Then: 예상된 동작 검증
        verify(service).listWiseSayings(1);
        verify(service).getTotalPages();
        verify(br).readLine();
    }
    @Test
    void ListTest2() throws IOException {
        // Given: Mock 데이터 준비
        List<WiseSaying> mockData = Arrays.asList(
                new WiseSaying(5, "명언 5", "작자미상 5"),
                new WiseSaying(4, "명언 4", "작자미상 4"),
                new WiseSaying(3, "명언 3", "작자미상 3"),
                new WiseSaying(2, "명언 2", "작자미상 2"),
                new WiseSaying(1, "명언 1", "작자미상 1")
        );
        when(br.readLine()).thenReturn("2"); // 페이지 번호 입력
        when(service.listWiseSayings(2)).thenReturn(mockData);
        when(service.getTotalPages()).thenReturn(2);

        // When: 테스트 메서드 실행
        controller.listWiseSayings();

        // Then: 예상된 동작 검증
        verify(service).listWiseSayings(2);
        verify(service).getTotalPages();
        verify(br).readLine();
    }

    @Test
    void deleteTest() throws IOException {
        when(br.readLine()).thenReturn("1");
        when(service.deleteWiseSaying(1)).thenReturn(true);

        controller.deleteWiseSaying();

        verify(service).deleteWiseSaying(1);
        verify(br).readLine();
    }

    @Test
    void updateTest() throws IOException {
        when(br.readLine())
                .thenReturn("1")
                .thenReturn("현재를 사랑하라.")
                .thenReturn("미상");
        when(service.updateWiseSaying(1,"현재를 사랑하라.","미상")).thenReturn(true);

        controller.updateWiseSaying();

        verify(service).updateWiseSaying(1,"현재를 사랑하라.","미상");
        verify(br,times(3)).readLine();
    }
}
