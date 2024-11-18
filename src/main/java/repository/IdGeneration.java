package repository;

public class IdGeneration {
	private Long id;

	public IdGeneration() {
		this.id = 1L;
	}

	public Long generationId() {
		return id++;
	}
}
