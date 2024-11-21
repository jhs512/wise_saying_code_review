package wisesaying.service;

import java.util.LinkedList;
import java.util.Scanner;

import wisesaying.domain.WiseSaying;

public interface WiseSayingService {
	Long add(String wiseSaying, String writer);
	LinkedList<WiseSaying> findAll();
	void delete(Long targetId);
	void update(Scanner sc);
	void build();
}
