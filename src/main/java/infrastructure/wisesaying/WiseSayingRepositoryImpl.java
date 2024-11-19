package infrastructure.wisesaying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import wisesaying.domain.WiseSaying;
import wisesaying.exception.WiseSayingException;

public class WiseSayingRepositoryImpl implements WiseSayingRepository {
	private final IdGeneration idGeneration;
	private final LinkedHashMap<Long, WiseSayingEntity> wiseSayingEntityLinkedHashMap;
	private static final String FILE_PATH = "db/wiseSaying/";
	private static final String LAST_ID_PATH = FILE_PATH + "lastId.txt";

	public WiseSayingRepositoryImpl() {
		Path lastIdPath = Paths.get(LAST_ID_PATH);
		Path wiseSayingPath = Paths.get(FILE_PATH + "data.json");

		try {
			if (Files.exists(lastIdPath)) {
				long id = Long.parseLong(Files.readString(lastIdPath));
				idGeneration = new IdGeneration(id);
			} else {
				idGeneration = new IdGeneration(0L);
			}

			if (Files.exists(wiseSayingPath)) {
				String json = Files.readString(wiseSayingPath);
				wiseSayingEntityLinkedHashMap = WiseSayingEntity.fromJsonList(json);
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

		Path wiseSayingPath = Paths.get(FILE_PATH + wiseSayingEntity.getId() + ".json");
		Path lastIdPath = Paths.get(LAST_ID_PATH);

		if (!Files.exists(wiseSayingPath)) {
			Files.createDirectories(wiseSayingPath.getParent());
			Files.createFile(wiseSayingPath);
		}

		if (!Files.exists(lastIdPath)) {
			Files.createFile(lastIdPath);
		}

		String wiseSayingEntityJson = wiseSayingEntity.toJson();

		Files.write(wiseSayingPath, wiseSayingEntityJson.getBytes());
		Files.write(lastIdPath, String.valueOf(id).getBytes());

		return id;
	}

	public Optional<WiseSaying> findById(Long id) throws IOException {
		Path wiseSayingPath = Paths.get(FILE_PATH + id + ".json");

		String json = Files.readString(wiseSayingPath);

		WiseSayingEntity wiseSayingEntity = WiseSayingEntity.fromJson(json);

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

	public Long delete(Long id) throws IOException {
		Path wiseSayingPash = Paths.get(FILE_PATH + id + ".json");
		WiseSayingEntity remove = wiseSayingEntityLinkedHashMap.remove(id);

		if (Objects.isNull(remove) || !Files.exists(wiseSayingPash)) {
			throw new WiseSayingException(id + "번 명언은 존재하지 않습니다.");
		}

		Files.delete(wiseSayingPash);
		return id;
	}

	public void update(WiseSaying wiseSaying) throws IOException {
		WiseSayingEntity wiseSayingEntity = WiseSayingEntity.from(wiseSaying);
		Path wiseSayingPath = Paths.get(FILE_PATH + wiseSayingEntity.getId() + ".json");
		wiseSayingEntityLinkedHashMap.replace(wiseSayingEntity.getId(), wiseSayingEntity);
		Files.write(wiseSayingPath, wiseSayingEntity.toJson().getBytes());
	}

	public void build() throws IOException {
		Path wiseSayingPath = Paths.get(FILE_PATH + "data.json");

		if (!Files.exists(wiseSayingPath)) {
			Files.createDirectories(wiseSayingPath.getParent());
			Files.createFile(wiseSayingPath);
		}

		if (!wiseSayingEntityLinkedHashMap.isEmpty()) {
			Files.write(wiseSayingPath, WiseSayingEntity.toJsonList(wiseSayingEntityLinkedHashMap).getBytes());
		}
	}
}
