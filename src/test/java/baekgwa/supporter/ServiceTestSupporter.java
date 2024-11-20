package baekgwa.supporter;

import baekgwa.entity.WiseSaying;
import baekgwa.repository.WiseSayingRepository;
import baekgwa.repository.mock.MockWiseSayingRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public abstract class ServiceTestSupporter {

    protected final WiseSaying baseWiseSaying = new WiseSaying(1L, "content", "author");

    protected WiseSayingRepository mockWiseSayingRepository
            = new MockWiseSayingRepository() {
        @Override
        public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {}

        @Override
        public List<WiseSaying> loadAllWiseSaying() throws IOException {
            return List.of(baseWiseSaying);
        }

        @Override
        public void deleteById(Long id) throws IOException, RuntimeException {
            if(!id.equals(baseWiseSaying.getId())) throw new RuntimeException();
        }

        @Override
        public boolean existById(Long id) {
            if(id.equals(1L)) return true;
            return false;
        }

        @Override
        public Optional<WiseSaying> findById(Long id) throws IOException {
            if(id.equals(1L)) return Optional.of(baseWiseSaying);
            return Optional.empty();
        }

        @Override
        public void build() throws IOException {}

        @Override
        public void saveLastId(Long index) throws IOException {}

        @Override
        public Long loadLastId() {
            return 0L;
        }

        @Override
        public void createDirectory() throws IOException {}

        @Override
        public void deleteAllData() throws IOException {}
    };

    protected WiseSayingRepository exceptionMockWiseSayingRepository = new MockWiseSayingRepository() {
        @Override
        public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
            throw new IOException();
        }

        @Override
        public List<WiseSaying> loadAllWiseSaying() throws IOException {
            throw new IOException();
        }

        @Override
        public void deleteById(Long id) throws IOException, RuntimeException {
            throw new IOException();
        }

        @Override
        public boolean existById(Long id) {
            return false;
        }

        @Override
        public Optional<WiseSaying> findById(Long id) throws IOException {
            throw new IOException();
        }

        @Override
        public void build() throws IOException {
            throw new IOException();
        }

        @Override
        public void saveLastId(Long index) throws IOException {
            throw new IOException();
        }

        @Override
        public Long loadLastId() {
            return -1L;
        }

        @Override
        public void createDirectory() throws IOException {
            throw new IOException();
        }

        @Override
        public void deleteAllData() throws IOException {
            throw new IOException();
        }
    };
}
