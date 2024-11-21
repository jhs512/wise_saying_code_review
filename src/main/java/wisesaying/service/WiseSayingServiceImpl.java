package wisesaying.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import infrastructure.wisesaying.WiseSayingRepository;
import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;

public class WiseSayingServiceImpl implements WiseSayingService {
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

	public Optional<Long> delete(Long targetId) {
		try {
			Long deletedId = wiseSayingRepository.delete(targetId);

			return Optional.of(deletedId);
		} catch (WiseSayingException | IOException e) {
			System.out.println(e.getMessage());
		}
		return Optional.empty();
	}

	public void update(WiseSaying targetWiseSaying, String wiseSaying, String writer) {
		try {
			targetWiseSaying.fetch(wiseSaying, writer);

			wiseSayingRepository.update(targetWiseSaying);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Boolean build() {
		try {
			return wiseSayingRepository.build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	@Override
	public WiseSaying findById(Long id) {
		try {
			return wiseSayingRepository.findById(id)
				.orElseThrow(() -> new WiseSayingException(id + "번 명언이 존재하지 않습니다."));
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new WiseSayingException(id + "번 명언이 존재하지 않습니다.");
	}
}
