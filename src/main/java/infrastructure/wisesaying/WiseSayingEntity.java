package infrastructure.wisesaying;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import wisesaying.domain.WiseSaying;

public class WiseSayingEntity {
	private Long id;
	private String wiseSaying;
	private String writer;

	public WiseSayingEntity() {
	}

	private WiseSayingEntity(Long id, String wiseSaying, String writer) {
		this.id = id;
		this.wiseSaying = wiseSaying;
		this.writer = writer;
	}

	public WiseSayingEntity(String wiseSaying, String writer) {
		this.wiseSaying = wiseSaying;
		this.writer = writer;
	}

	public Long getId() {
		return id;
	}

	public String getWiseSaying() {
		return wiseSaying;
	}

	public String getWriter() {
		return writer;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WiseSaying toModel() {
		return new WiseSaying(id, wiseSaying, writer);
	}

	public static WiseSayingEntity from(WiseSaying wiseSaying) {
		return new WiseSayingEntity(wiseSaying.getId(), wiseSaying.getWiseSaying(), wiseSaying.getWriter());
	}

	public String toJson() {
		return "{" + "\"id\": \"" + id + "\"," +
			"\"wiseSaying\": \"" + wiseSaying + "\"," +
			"\"writer\": \"" + writer + "\"}";
	}

	public static String toJsonList(LinkedHashMap<Long, WiseSayingEntity> linkedHashMap) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		linkedHashMap.values()
			.stream()
			.forEach(wiseSayingEntity -> sb.append(wiseSayingEntity.toJson() + ","));

		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");

		return sb.toString();
	}

	public static WiseSayingEntity fromJson(String json) {
		String[] array = json.trim().replaceAll("[{}\"]", "").split(",");

		Map<String, String> map = Arrays.stream(array).map(split -> split.split(":"))
			.collect(Collectors.toMap(keyValue -> keyValue[0].trim(),
				keyValue -> keyValue[1].trim()));

		return new WiseSayingEntity(Long.parseLong(map.get("id")), map.get("wiseSaying"), map.get("writer"));
	}

	public static LinkedHashMap<Long, WiseSayingEntity> fromJsonList(String json) {
		String substring = json.substring(1, json.length() - 1);
		String[] jsonArray = substring.split("},\\{");

		return Arrays.stream(jsonArray)
			.map(jsonObject -> WiseSayingEntity.fromJson(jsonObject))
			.collect(Collectors.toMap(
				WiseSayingEntity::getId,
				wiseSayingEntity -> wiseSayingEntity,
				(oldValue, newValue) -> oldValue,
				LinkedHashMap::new
			));
	}
}
