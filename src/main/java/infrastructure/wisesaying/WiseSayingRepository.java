package infrastructure.wisesaying;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;

import wisesaying.domain.WiseSaying;

public interface WiseSayingRepository {
	Long add(WiseSaying wiseSaying) throws IOException;
	Optional<WiseSaying> findById(Long id) throws IOException;
	Optional<LinkedList<WiseSaying>> findAll() throws IOException;
	Long delete(Long id) throws IOException;
	void update(WiseSaying wiseSaying) throws IOException;
	Boolean build() throws IOException;
}
