package wisesaying.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import infrastructure.MemoryWiseSayingRepository;
import infrastructure.wisesaying.WiseSayingRepository;
import infrastructure.wisesaying.WiseSayingRepositoryImpl;
import wisesaying.TestUtil;
import wisesaying.domain.WiseSaying;
import wisesaying.service.WiseSayingService;
import wisesaying.service.WiseSayingServiceImpl;
import wisesaying.service.response.WiseSayingPageResponse;
import wisesaying.util.WiseSayingCondition;

class WiseSayingControllerTest {
	WiseSayingRepository wiseSayingRepository;
	WiseSayingService wiseSayingService;

	static final String INPUT_ADD_SUCCESS =
		"등록\n"
		+ "명언1\n"
		+ "작가1\n"
		+ "종료\n";
	static final String INPUT_UPDATE_SUCCESS =
		"수정\n"
		+ "1\n"
		+ "명언수정1\n"
		+ "작가수정1\n"
		+ "종료\n";
	static final String INPUT_UPDATE_TYPE_MISMATCH_FAIL =
		"수정\n"
		+ "ㄴ\n"
		+ "종료";
	static final String INPUT_DELETE_SUCCESS =
		"삭제\n"
		+ "1\n"
		+ "종료";
	static final String INPUT_DELETE_TYPE_MISMATCH_FAIL =
		"삭제\n"
		+ "ㄴ\n"
		+ "종료";
	static final String INPUT_FINDALL_SUCCESS =
		"목록?page=2\n"
		+ "종료";
	static final String INPUT_BUILD_SUCCESS =
		"빌드"
		+ "\n종료";

	@BeforeEach
	void setUp() {
		wiseSayingRepository = new MemoryWiseSayingRepository();
		wiseSayingService = new WiseSayingServiceImpl(wiseSayingRepository);
	}

	@Test
	@DisplayName("add_success")
	void add_success() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner("등록\n명언1\n작가1\n종료\n"));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
		WiseSaying wiseSaying = wiseSayingRepository.findById(1L).orElseThrow(() -> new RuntimeException());

		assertTrue(output.contains(1L + "번 명언이 등록되었습니다."));
		assertEquals(wiseSaying.getId(), 1L);
		assertEquals(wiseSaying.getWiseSaying(), "명언1");
		assertEquals(wiseSaying.getWriter(), "작가1");
	}

	@Test
	@DisplayName("update_wiseSaying_success")
	void update_success() throws IOException {
		wiseSayingRepository.add(WiseSaying.createWiseSaying("명언1", "작가1"));
		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_UPDATE_SUCCESS));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
		WiseSaying wiseSaying = wiseSayingRepository.findById(1L).orElseThrow(() -> new RuntimeException());

		assertEquals(wiseSaying.getId(), 1L);
		assertEquals(wiseSaying.getWiseSaying(), "명언수정1");
		assertEquals(wiseSaying.getWriter(), "작가수정1");
	}

	@Test
	@DisplayName("update_type_mismatch_fail")
	void update_type_mismatch_fail() throws IOException {
		wiseSayingRepository.add(WiseSaying.createWiseSaying("명언1", "작가1"));
		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_UPDATE_TYPE_MISMATCH_FAIL));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

		assertTrue(output.contains("숫자만 입력 가능합니다."));
	}

	@DisplayName("delete_success")
	@Test
	void delete_success() throws IOException {
		wiseSayingRepository.add(WiseSaying.createWiseSaying("명언1", "작가1"));
		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_DELETE_SUCCESS));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

		assertTrue(output.contains(1L + "번 명언이 삭제되었습니다."));
		assertTrue(wiseSayingRepository.findAll().get().isEmpty());
	}

	@DisplayName("delete_type_mismatch_fail")
	@Test
	void delete_type_mismatch_fail() throws IOException {
		wiseSayingRepository.add(WiseSaying.createWiseSaying("명언1", "작가1"));
		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_DELETE_TYPE_MISMATCH_FAIL));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

		assertTrue(output.contains("숫자만 입력 가능합니다."));
	}

	@DisplayName("findAll_success")
	@Test
	void findAll_success() throws IOException {
		String givenWiseSaying = "명언";
		String givenWriter = "작가";

		for (int i = 0; i < 13; i++) {
			wiseSayingRepository.add(WiseSaying.createWiseSaying(givenWiseSaying + i+1, givenWriter + i+1));
		}

		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_FINDALL_SUCCESS));

		wiseSayingController.run();

		StringBuilder sb = new StringBuilder();

		WiseSayingPageResponse wiseSayingPageResponse = wiseSayingService.findAll(
			new WiseSayingCondition(2L, null, null));

		wiseSayingPageResponse.getWiseSayingLinkedList().forEach(
			(wiseSaying) ->
				sb.append(wiseSaying.getId() + " / " + wiseSaying.getWriter() + " / " + wiseSaying.getWiseSaying() + " \n")
		);

		sb.append("페이지 : " + wiseSayingPageResponse.getPageNum() + " / [" + wiseSayingPageResponse.getPageSize() + "]");

		String wiseSayingListString = sb.toString();
		String output = byteArrayOutputStream.toString().trim();

		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);
		assertTrue(output.contains(wiseSayingListString));
	}

	@DisplayName("build_success")
	@Test
	void build_success() throws IOException {
		String givenWiseSaying = "명언";
		String givenWriter = "작가";

		for (int i = 0; i < 3; i++) {
			wiseSayingRepository.add(WiseSaying.createWiseSaying(givenWiseSaying + i+1, givenWriter + i+1));
		}

		ByteArrayOutputStream byteArrayOutputStream =
			TestUtil.setOutToByteArray();

		WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService,
			TestUtil.genScanner(INPUT_BUILD_SUCCESS));

		wiseSayingController.run();

		String output = byteArrayOutputStream.toString().trim();
		TestUtil.clearSetOutToByteArray(byteArrayOutputStream);

		assertTrue(output.contains("data.json 파일의 내용이 갱신되었습니다."));
	}
}