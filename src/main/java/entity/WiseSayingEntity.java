package entity;

public class WiseSayingEntity {
	private Long id;
	private String wiseSaying;
	private String writer;

	public WiseSayingEntity(String wiseSaying, String writer) {
		this.wiseSaying = wiseSaying;
		this.writer = writer;
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
}
