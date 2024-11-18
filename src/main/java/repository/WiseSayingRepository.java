package repository;

import java.util.LinkedHashMap;

import entity.WiseSayingEntity;

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

}
