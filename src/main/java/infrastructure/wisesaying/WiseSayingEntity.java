package infrastructure.wisesaying;

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
}
