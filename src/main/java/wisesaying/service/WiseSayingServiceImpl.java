package wisesaying.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import infrastructure.wisesaying.WiseSayingRepository;
import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;

public class WiseSayingServiceImpl implements WiseSayingService{
	private final WiseSayingRepository wiseSayingRepository;

	public WiseSayingServiceImpl(WiseSayingRepository wiseSayingRepository) {
		this.wiseSayingRepository = wiseSayingRepository;
	}

	public Long add(String wiseSaying, String writer) {
		Long savedId = 0L;
		try {
			WiseSaying createWiseSaying = WiseSaying.createWiseSaying(wiseSaying, writer);
			savedId = wiseSayingRepository.add(createWiseSaying);

			return savedId;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return savedId;
	}

	public LinkedList<WiseSaying> findAll() {
		try {
			Optional<LinkedList<WiseSaying>> findAll = wiseSayingRepository.findAll();
			LinkedList<WiseSaying> wiseSayingLinkedList = findAll.orElseGet(() -> new LinkedList<>());

			return wiseSayingLinkedList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	public void delete(Long targetId) {
		try {
			Long deletedId = wiseSayingRepository.delete(targetId);

			System.out.println(deletedId + "번 명언이 삭제되었습니다.");
		} catch (WiseSayingException | IOException e) {
			System.out.println(e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요.");
		}
	}

	public void update(Scanner sc) {
		try {
			System.out.print("수정?id = ");
			Long target = sc.nextLong();
			sc.nextLine();

			Optional<WiseSaying> wiseSaying = wiseSayingRepository.findById(target);

			if (wiseSaying.isEmpty()) {
				System.out.println(target + "번 명언이 존재하지 않습니다.");
				return;
			}

			WiseSaying targetWiseSaying = wiseSaying.get();

			System.out.println("명언 (기존) : " + targetWiseSaying.getWiseSaying());
			System.out.print("명언 : ");
			String updateWiseSaying = sc.nextLine();

			System.out.println("작가 (기존) : " + targetWiseSaying.getWriter());
			System.out.print("작가 : ");
			String updateWriter = sc.nextLine();

			targetWiseSaying.fetch(updateWiseSaying, updateWriter);

			wiseSayingRepository.update(targetWiseSaying);
		} catch (WiseSayingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void build() {
		try {
			wiseSayingRepository.build();
			System.out.println("data.json 파일의 내용이 갱신되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
