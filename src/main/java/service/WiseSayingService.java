package service;

import java.util.Scanner;

import entity.WiseSayingEntity;
import repository.WiseSayingRepository;

public class WiseSayingService {
	private static final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

	public void add(Scanner sc) {
		System.out.print("명언 : ");
		String wiseSaying = sc.nextLine();
		System.out.print("작가 : ");
		String writer = sc.nextLine();
		WiseSayingEntity wiseSayingEntity = new WiseSayingEntity(wiseSaying, writer);
		Long savedId = wiseSayingRepository.add(wiseSayingEntity);
		System.out.println(savedId + "번 명언이 등록되었습니다.");
	}
}
