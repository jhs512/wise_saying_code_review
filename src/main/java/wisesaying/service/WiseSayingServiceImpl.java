package wisesaying.service;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import infrastructure.wisesaying.WiseSayingRepository;
import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;
import wisesaying.service.response.WiseSayingPageResponse;
import wisesaying.util.WiseSayingCondition;

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

	public WiseSayingPageResponse findAll(WiseSayingCondition wiseSayingCondition) {
		try {
			Optional<LinkedList<WiseSaying>> findAll = wiseSayingRepository.findAll();
			LinkedList<WiseSaying> wiseSayingLinkedList = findAll.orElseGet(() -> new LinkedList<>());

			Long totalPage = (long)(wiseSayingLinkedList.size() % 5);

			LinkedList<WiseSaying> collect = wiseSayingLinkedList.stream().filter(
					wiseSaying ->
						(wiseSayingCondition.wiseSaying() == null || wiseSaying.getWiseSaying()
							.equals(wiseSayingCondition.wiseSaying())) &&
							(wiseSayingCondition.writer() == null || wiseSaying.getWriter()
								.equals(wiseSayingCondition.writer()))
				)
				.skip(wiseSayingCondition.pageNum() == null ? 0 : (wiseSayingCondition.pageNum() - 1) * 5)
				.limit(5)
				.collect(Collectors.toCollection(LinkedList::new));

			return new WiseSayingPageResponse(wiseSayingCondition.pageNum(), totalPage, collect);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return WiseSayingPageResponse.createEmptyPageResponse();
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
