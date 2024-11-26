package org.example.mvc.repository;

import org.example.constants.GlobalConstants;
import org.example.utils.Parser;
import org.example.entity.WiseSaying;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WiseSayingRepositoryImpl implements WiseSayingRepository {

    private static final HashMap<Integer, WiseSaying> store = new HashMap<>();
    private int lastId = 0;

    @Override
    public int getId() {
        return lastId;
    }

    @Override
    public void setId(int id) { lastId = id; }

    @Override
    public WiseSaying save(WiseSaying wiseSaying) {
        lastId++;
        wiseSaying.setId(lastId);
        store.put(lastId, wiseSaying);
        return wiseSaying;
    }

    @Override
    public void saveById(int id, WiseSaying wiseSaying) {
        store.put(id, wiseSaying);
    }

    @Override
    public Optional<WiseSaying> findById(int id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<WiseSaying> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(int id) {
        store.remove(id);
    }

    @Override
    public void updateById(int id, WiseSaying wiseSaying) {
        store.put(id, wiseSaying);
    }

    // 명언 객체를 json 파일로 저장
    @Override
    public void storeJson(WiseSaying wiseSaying) {
        String StringOfWiseSaying = Parser.convertWiseSayingToString(wiseSaying, false);

        String path = GlobalConstants.PATH + wiseSaying.getId() + GlobalConstants.JSON_EXTENSION;
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(StringOfWiseSaying);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
    }

    // 최종 등록 명언 id lastId.txt 에 저장
    @Override
    public void storeLastId(int id) {
        // 마지막 이름 lastId.txt 로 저장
        String path = GlobalConstants.PATH + GlobalConstants.LAST_ID_FILE;
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(String.valueOf(id));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 모든 wiseSaying json 문자열 합쳐서 data.json 파일 생성
    @Override
    public void build() {
        List<WiseSaying> wiseSayingList = findAll();

        String buildResult = Parser.convertWiseSayingListToString(wiseSayingList);

        String path = GlobalConstants.PATH + GlobalConstants.BUILD_FILE;
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(buildResult);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일 내용을 문자열로 읽어오기
    @Override
    public String getStringFromFile(String fileName) {
        Path filePath = Paths.get(GlobalConstants.PATH + fileName);

        String content = "";
        try {
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // 서버가 시작될 때 모든 id.json파일 불러오기
    @Override
    public void initRepository() {
        Path folderPath = Paths.get(GlobalConstants.PATH);

        // 디렉토리가 존재하고, 디렉토리인지 확인
        if (Files.isDirectory(folderPath)) {
            try {
                // 디렉토리 내의 파일을 탐색
                Files.walk(folderPath)
                        .filter(Files::isRegularFile) // 파일만 필터링
                        .forEach(filePath -> processFile(filePath));
            } catch (IOException e) {
                e.printStackTrace(); // 예외 처리
            }
        }
    }

    // 파일 1개 불러오기
    @Override
    public void processFile(Path filePath) {
        String fileName = filePath.getFileName().toString().trim();

        // data.json 은 제외
        if (fileName.equals(GlobalConstants.BUILD_FILE)) return;

        if(fileName.equals(GlobalConstants.LAST_ID_FILE)) {
            lastId = Integer.parseInt(getStringFromFile(fileName));
        } else if (fileName.endsWith(GlobalConstants.JSON_EXTENSION)) {
            String wiseSayingString = getStringFromFile(fileName);
            WiseSaying wiseSaying = Parser.convertStringToWiseSaying(wiseSayingString);
            saveById(wiseSaying.getId(), wiseSaying);
        }
    }

    @Override
    public void createDirectory() {
        Path dirPath = Paths.get(GlobalConstants.PATH);
        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJsonById(int id) {
        String path = GlobalConstants.PATH + id + GlobalConstants.JSON_EXTENSION;
        File file = new File(path);
        file.delete();
    }

    @Override
    public void deleteBuildFile() {
        String path = GlobalConstants.PATH + "data.json";
        File file = new File(path);
        file.delete();
    }
    @Override
    public void deleteLastIdFile() {
        String path = GlobalConstants.PATH + "lastId.txt";
        File file = new File(path);
        file.delete();
    }

}
