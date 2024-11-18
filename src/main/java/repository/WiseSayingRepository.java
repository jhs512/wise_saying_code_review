package repository;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;

import entity.WiseSayingEntity;
import exception.WiseSayingException;

public class WiseSayingRepository {
	private final IdGeneration idGeneration = new IdGeneration();
	private final LinkedHashMap<Long, WiseSayingEntity> wiseSayingEntityLinkedHashMap = new LinkedHashMap<>();

	public Long add(WiseSayingEntity wiseSayingEntity) {
		Long id = idGeneration.generationId();
		wiseSayingEntity.setId(id);
		wiseSayingEntityLinkedHashMap.put(id, wiseSayingEntity);

		return id;
	}

	public WiseSayingEntity findById(Long id) {
		return wiseSayingEntityLinkedHashMap.get(id);
	}

	public LinkedList<WiseSayingEntity> findAll() {
		return new LinkedList<>(wiseSayingEntityLinkedHashMap.values());
	}

	public Long delete(Long id) {
		WiseSayingEntity remove = wiseSayingEntityLinkedHashMap.remove(id);

		if (Objects.isNull(remove)) {
			throw new WiseSayingException(id + "번 명언은 존재하지 않습니다.");
		}

		return id;
	}

	public void update(WiseSayingEntity wiseSayingEntity) {
		wiseSayingEntityLinkedHashMap.replace(wiseSayingEntity.getId(), wiseSayingEntity);
	}
}
