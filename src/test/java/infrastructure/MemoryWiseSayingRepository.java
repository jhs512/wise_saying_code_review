package infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import infrastructure.wisesaying.IdGeneration;
import infrastructure.wisesaying.WiseSayingEntity;
import infrastructure.wisesaying.WiseSayingRepository;
import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;

public class MemoryWiseSayingRepository implements WiseSayingRepository {
	private final IdGeneration idGeneration;
	private final LinkedHashMap<Long, WiseSayingEntity> wiseSayingEntityLinkedHashMap;

	public MemoryWiseSayingRepository() {
		idGeneration = new IdGeneration(0L);
		wiseSayingEntityLinkedHashMap = new LinkedHashMap<>();
	}

	public Long add(WiseSaying wiseSaying) throws IOException {
		Long id = idGeneration.generationId();
		WiseSayingEntity wiseSayingEntity = new WiseSayingEntity(wiseSaying.getWiseSaying(), wiseSaying.getWriter());
		wiseSayingEntity.setId(id);
		wiseSayingEntityLinkedHashMap.put(id, wiseSayingEntity);

		String wiseSayingEntityJson = wiseSayingEntity.toJson();

		return id;
	}

	public Optional<WiseSaying> findById(Long id) throws IOException {
		WiseSayingEntity wiseSayingEntity = wiseSayingEntityLinkedHashMap.get(id);

		if (Objects.isNull(wiseSayingEntity)) {
			return Optional.empty();
		}

		WiseSaying wiseSaying = new WiseSaying(
			wiseSayingEntity.getId(),
			wiseSayingEntity.getWiseSaying(),
			wiseSayingEntity.getWriter()
		);

		return Optional.of(wiseSaying);
	}

	public Optional<LinkedList<WiseSaying>> findAll() throws IOException {
		if (wiseSayingEntityLinkedHashMap.isEmpty()) {
			return Optional.of(new LinkedList<>());
		}

		return Optional.of(wiseSayingEntityLinkedHashMap.values().stream()
			.map(WiseSayingEntity::toModel)
			.collect(Collectors.toCollection(LinkedList::new)));
	}

	public Long delete(Long id) throws IOException {
		WiseSayingEntity remove = wiseSayingEntityLinkedHashMap.remove(id);
		if (remove == null) {
			System.out.println(id + "번 명언이 존재하지 않습니다.");
		}
		return id;
	}

	public void update(WiseSaying wiseSaying) throws IOException {
		WiseSayingEntity wiseSayingEntity = WiseSayingEntity.from(wiseSaying);
		wiseSayingEntityLinkedHashMap.replace(wiseSayingEntity.getId(), wiseSayingEntity);
	}

	public Boolean build() throws IOException {
		if (wiseSayingEntityLinkedHashMap.isEmpty()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	void clear() {
		wiseSayingEntityLinkedHashMap.clear();
		idGeneration.resetId();
	}
}
