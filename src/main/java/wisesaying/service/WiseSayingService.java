package wisesaying.service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Scanner;

import wisesaying.domain.WiseSaying;
import wisesaying.service.response.WiseSayingPageResponse;
import wisesaying.util.WiseSayingCondition;

public interface WiseSayingService {
	Long add(String wiseSaying, String writer);
	WiseSayingPageResponse findAll(WiseSayingCondition wiseSayingCondition);
	Optional<Long> delete(Long targetId);
	void update(WiseSaying targetWiseSaying, String wiseSaying, String writer);
	WiseSaying findById(Long id);
	Boolean build();
}
