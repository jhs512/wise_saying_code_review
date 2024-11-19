package infrastructure.wisesaying;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;

public class WiseSayingRepositoryImpl implements WiseSayingRepository{
	private final IdGeneration idGeneration;
	private final LinkedHashMap<Long, WiseSayingEntity> wiseSayingEntityLinkedHashMap;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String FILE_PATH="db/wiseSaying/";
	private static final String LAST_ID_PATH="db/wiseSaying/lastId.txt";

	public WiseSayingRepositoryImpl() {
		File lastIdFile = new File(LAST_ID_PATH);
		File wiseSayingFile = new File(FILE_PATH + "data.json");

		try {
			if (lastIdFile.exists()) {
				Long id = objectMapper.readValue(lastIdFile, Long.class);
				idGeneration = new IdGeneration(id);
			} else {
				idGeneration = new IdGeneration(0L);
			}

			if (wiseSayingFile.exists()) {
				LinkedList<WiseSayingEntity> wiseSayingEntities = objectMapper.readValue(wiseSayingFile,
					new TypeReference<LinkedList<WiseSayingEntity>>() {});

				wiseSayingEntityLinkedHashMap = wiseSayingEntities.stream()
					.collect(Collectors.toMap(
						WiseSayingEntity::getId,
						entity -> entity,
						(oldValue, newValue) -> oldValue,
						LinkedHashMap::new
					));
			} else {
				wiseSayingEntityLinkedHashMap = new LinkedHashMap<>();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Long add(WiseSaying wiseSaying) throws IOException {
		Long id = idGeneration.generationId();
		WiseSayingEntity wiseSayingEntity = new WiseSayingEntity(wiseSaying.getWiseSaying(), wiseSaying.getWriter());
		wiseSayingEntity.setId(id);
		wiseSayingEntityLinkedHashMap.put(id, wiseSayingEntity);

		File wiseSayingFile = new File(FILE_PATH + wiseSayingEntity.getId() + ".json");
		File lastIdFile = new File(LAST_ID_PATH);

		if (!wiseSayingFile.exists()) {
			wiseSayingFile.getParentFile().mkdirs();
			wiseSayingFile.createNewFile();
		}

		if (!lastIdFile.exists()) {
			lastIdFile.getParentFile().mkdirs();
			lastIdFile.createNewFile();
		}

		objectMapper.writeValue(wiseSayingFile, wiseSayingEntity);
		objectMapper.writeValue(lastIdFile, id);

		return id;
	}

	public Optional<WiseSaying> findById(Long id) throws IOException {
		File wiseSayingFile = new File(FILE_PATH + id + ".json");
		WiseSayingEntity wiseSayingEntity = objectMapper.readValue(wiseSayingFile, WiseSayingEntity.class);

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
			return Optional.empty();
		}

		return Optional.of(wiseSayingEntityLinkedHashMap.values().stream()
			.map(WiseSayingEntity::toModel)
			.collect(Collectors.toCollection(LinkedList::new)));
	}

	public Long delete(Long id) {
		File wiseSayingFile = new File(FILE_PATH + id + ".json");
		WiseSayingEntity remove = wiseSayingEntityLinkedHashMap.remove(id);

		if (Objects.isNull(remove) || !wiseSayingFile.exists()) {
			throw new WiseSayingException(id + "번 명언은 존재하지 않습니다.");
		}

		wiseSayingFile.delete();
		return id;
	}

	public void update(WiseSaying wiseSaying) throws IOException {
		WiseSayingEntity wiseSayingEntity = WiseSayingEntity.from(wiseSaying);
		File wiseSayingFile = new File(FILE_PATH + wiseSayingEntity.getId() + ".json");
		wiseSayingEntityLinkedHashMap.replace(wiseSayingEntity.getId(), wiseSayingEntity);
		objectMapper.writeValue(wiseSayingFile, wiseSayingEntity);
	}

	public void build() throws IOException {
		LinkedList<WiseSayingEntity> wiseSayingEntities = new LinkedList<>(wiseSayingEntityLinkedHashMap.values());

		File wiseSayingFile = new File(FILE_PATH + "data.json");

		if (!wiseSayingFile.exists()) {
			wiseSayingFile.getParentFile().mkdirs();
			wiseSayingFile.createNewFile();
		}

		objectMapper.writeValue(wiseSayingFile, wiseSayingEntities);
	}
}
