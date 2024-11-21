package baekgwa.repository;

import baekgwa.entity.WiseSaying;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {

    void saveWiseSaying(WiseSaying wiseSaying) throws IOException;

    List<WiseSaying> loadAllWiseSaying() throws IOException;

    void deleteById(Long id) throws IOException;
    boolean existById(Long id);
    Optional<WiseSaying> findById(Long id) throws IOException;
    void build() throws IOException;

    void saveLastId(Long index) throws IOException;
    Long loadLastId();

    void createDirectory() throws IOException;
    void deleteAllData() throws IOException;
}
