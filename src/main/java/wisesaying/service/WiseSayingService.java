package wisesaying.service;

import java.util.Scanner;

public interface WiseSayingService {
	Long add(String wiseSaying, String writer);
	void findAll();
	void delete(Long targetId);
	void update(Scanner sc);
	void build();
}
