package baekgwa.repository;

import static baekgwa.global.GlobalVariable.*;

import baekgwa.entity.WiseSaying;
import baekgwa.global.exception.CustomException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WiseSayingRepositoryImpl implements WiseSayingRepository {

    @Override
    public void saveWiseSaying(WiseSaying wiseSaying) throws IOException {
        String filePath = DB_PATH + wiseSaying.getId() + ".json";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            writer.write(wiseSaying.toJson());
        }
    }

    @Override
    public List<WiseSaying> loadAllWiseSaying() throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(DB_PATH))) {
            return paths
                    .filter(a -> a.toString().endsWith(".json"))
                    .filter(a -> !a.toString().contains("data.json"))
                    .map(a -> {
                        String content;
                        try {
                            content = Files.readString(a);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return WiseSaying.toObject(content);
                    })
                    .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            //람다 내부 체크 예외 처리 불가능 EX) IOException;
            //따라서, RuntimeException 으로 감싸고, RuntimeException 으로 변경 전파.
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) throws IOException {
        Path filePath = Paths.get(DB_PATH + id + ".json");
        Files.delete(filePath);
    }

    @Override
    public boolean existById(Long id) {
        Path filePath = Paths.get(DB_PATH + id + ".json");
        return Files.exists(filePath);
    }

    @Override
    public Optional<WiseSaying> findById(Long id) throws IOException {
        try {
            Path filePath = Paths.get(DB_PATH + id + ".json");
            return Optional.of(WiseSaying.toObject(Files.readString(filePath)));
        } catch (NoSuchFileException e) {
            return Optional.empty();
        }
    }

    @Override
    public void build() throws IOException {
        List<WiseSaying> wiseSayings = loadAllWiseSaying();
        wiseSayings.sort((a, b) -> Long.compare(a.getId(), b.getId()));

        StringBuilder jsonBuilder = new StringBuilder("[\n");
        for (int i = 0; i < wiseSayings.size(); i++) {
            WiseSaying wiseSaying = wiseSayings.get(i);
            jsonBuilder.append("  {\n")
                    .append("    \"id\": ").append(wiseSaying.getId()).append(",\n")
                    .append("    \"content\": \"")
                    .append(wiseSaying.getContent().replace("\"", "\\\"")).append("\",\n")
                    .append("    \"author\": \"")
                    .append(wiseSaying.getAuthor().replace("\"", "\\\"")).append("\"\n")
                    .append("  }");

            if (i < wiseSayings.size() - 1) {
                jsonBuilder.append(",");
            }
            jsonBuilder.append("\n");
        }
        jsonBuilder.append("]");

        Path path = Paths.get(DB_PATH + BUILD_FILE);

        // 파일 삭제 및 새로 작성
        Files.deleteIfExists(path);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonBuilder.toString());
        }
    }

    @Override
    public void saveLastId(Long index) throws IOException {
        String lastIdPath = DB_PATH + LAST_ID_FILE;
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(lastIdPath))) {
            writer.write(String.valueOf(index));
        }
    }

    @Override
    public Long loadLastId() {
        String lastIdPathStr = DB_PATH + LAST_ID_FILE;
        Path lastIdPath = Paths.get(lastIdPathStr);

        if (Files.exists(lastIdPath)) {
            return readLastIdFromFile(lastIdPath).orElse(0L);
        }

        return 0L;
    }

    @Override
    public void createDirectory() throws IOException {
        Path dirPath = Paths.get(DB_PATH);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    @Override
    public void deleteAllData() throws IOException {
        Path dirPath = Paths.get(DB_PATH);

        try {
            Files.walk(dirPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException("파일 삭제 실패: " + path, e);
                        }
                    });
        } catch (RuntimeException e) {
            throw new IOException(e.getMessage());
        }
    }

    private Optional<Long> readLastIdFromFile(Path path) {
        try {
            String readData = Files.readString(path).trim();
            if (!readData.isEmpty()) {
                return toLong(readData);
            }
        } catch (IOException e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private Optional<Long> toLong(String readData) {
        try {
            return Optional.of(Long.parseLong(readData));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
