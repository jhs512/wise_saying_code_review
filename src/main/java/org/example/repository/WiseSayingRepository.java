package org.example.repository;

import org.example.util.Converter;
import org.example.model.PageResult;
import org.example.model.WiseSayingItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.constatnt.Constant.*;

public class WiseSayingRepository {

    private static long id = 0L;
    // 임시 저장소
    private static final Map<Long, WiseSayingItem> dataJsonStore = new HashMap<>();
    private final String filePath;

    public WiseSayingRepository(String filePath) {
        this.filePath = filePath;
    }

    public long save(String wiseSaying, String author) {

        int lastId = loadLastNumber();
        if (lastId != 0) {
            id = lastId;
        }

        WiseSayingItem item = WiseSayingItem.of(++id, author, wiseSaying);
        saveFile(item);
        // save 할때마다 라스트 넘버도 저장
        saveLastFileNumber();
        return id;
    }

    //todo page 기능 도입
    public PageResult findAll(String currentPage) {

        List<WiseSayingItem> items = loadFiles();
        int pageSize = 5;
        int totalPage = (items.size() + pageSize - 1) / pageSize;

        if (currentPage.equals("")) {
            currentPage = "1";
        }

        int parsedCurrentPage = Integer.parseInt(currentPage);

        if (parsedCurrentPage < 1 || parsedCurrentPage > totalPage) {
            throw new IllegalArgumentException("페이지 숫자가 유효하지 않습니다, 전체 페이지 수 : " + totalPage);
        }

        List<String> result = items.stream()
                .skip((Long.parseLong(currentPage) - 1) * pageSize)
                .limit(pageSize)
                .map(item -> item.getId() + " / "
                        + item.getAuthor() + " / "
                        + item.getWiseSaying())
                .collect(Collectors.toList());

        return PageResult.of(result, totalPage, currentPage);
    }

    public WiseSayingItem findById(String id) {
        return loadFile(id);
    }

    public List<String> findByKeywords(String keywordType, String keyword) {
        return loadFiles(keywordType, keyword).stream()
                .map(item -> item.getId() + " / "
                        + item.getWiseSaying() + " / "
                        + item.getAuthor())
                .collect(Collectors.toList());
    }


    private WiseSayingItem loadFile(String id) {
        String fileName = id + EXTENSION_JSON.getData();
        Path path = Path.of(filePath + "/" + fileName);
        String jsonString = "";

        try {
            jsonString = Files.readString(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("해당 id의 명언은 존재하지 않습니다.", e);
        }
        Converter converter = new Converter();
        return converter.parseToJava(jsonString);
    }

    private List<WiseSayingItem> loadFiles() {
        // listFiles() 는 순서가 보장되지 않는다.
        File[] files = new File(filePath).listFiles();
        String jsonString = "";
        List<WiseSayingItem> items = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".json") && !file.getName().startsWith("data")) {
                    try {
                        jsonString = Files.readString(file.toPath());
                    } catch (IOException e) {
                        throw new IllegalArgumentException("파일을 읽어올 수 없습니다. 경로와 이름을 확인해주세요.", e);
                    }
                    Converter converter = new Converter();
                    WiseSayingItem item = converter.parseToJava(jsonString);
                    items.add(item);
                }
            }
        }
        items.sort(Comparator.comparingLong(WiseSayingItem::getId).reversed());
        return items;
    }

    private List<WiseSayingItem> loadFiles(String keywordType, String keyword) {
        // listFiles() 는 순서가 보장되지 않는다.
        File[] files = new File(filePath).listFiles();
        String jsonString = "";
        List<WiseSayingItem> items = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".json") && !file.getName().startsWith("data")) {
                    try {
                        jsonString = Files.readString(file.toPath());
                    } catch (IOException e) {
                        throw new IllegalArgumentException("파일을 읽어올 수 없습니다. 경로와 이름을 확인해주세요.", e);
                    }
                    Converter converter = new Converter();
                    WiseSayingItem item = converter.parseToJava(jsonString);
                    if (keywordType.equals("content") && item.getWiseSaying().contains(keyword)) {
                        items.add(item);
                    } else if (keywordType.equals("author") && item.getAuthor().contains(keyword)) {
                        items.add(item);
                    }
                }
            }
        }
        items.sort(Comparator.comparingLong(WiseSayingItem::getId));
        return items;
    }

    public String remove(String id) {
        String fileName = id + EXTENSION_JSON.getData();
        File file = new File(filePath, fileName);


        // 임시 저장소에는 id 값이 있으면 삭제
        if (dataJsonStore.get(Long.parseLong(id)) != null) {
            dataJsonStore.remove(Long.parseLong(id));
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("해당 id의 명언은 존재하지 않습니다.");
        } else {
            file.delete();
            return id;
        }
    }


    public void updateWiseSaying(String id, String wiseSaying) {


        // 임시 저장소에 있으면 업데이트
        if (dataJsonStore.get(Long.parseLong(id)) != null) {
            WiseSayingItem item = dataJsonStore.get(Long.parseLong(id));
            item.changeWiseSaying(wiseSaying);
            dataJsonStore.put(Long.parseLong(id), item);

        }

        WiseSayingItem item = findById(id);
        item.changeWiseSaying(wiseSaying);
        saveFile(item);
    }

    public void updateAuthor(String id, String author) {

        // 임시 저장소에 있으면 업데이트
        if (dataJsonStore.get(Long.parseLong(id)) != null) {
            WiseSayingItem item = dataJsonStore.get(Long.parseLong(id));
            item.changeWiseSaying(author);
            dataJsonStore.put(Long.parseLong(id), item);
        }

        WiseSayingItem item = findById(id);
        item.changeAuthor(author);
        saveFile(item);
    }

    private int loadLastNumber() {

        File file = new File(filePath, LAST_ID_FILE_NAME.getData() + EXTENSION_TXT.getData());

        if (!file.exists()) {
            return 0;
        } else {
            try (Scanner Filescanner = new Scanner(file)) {
                if (Filescanner.hasNextInt()) {
                    return Filescanner.nextInt();
                }
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("파일을 읽는데 실패하였습니다.", e);
            }

        }
        return 0;
    }

    public void saveLastFileNumber() {

        File file = new File(filePath, LAST_ID_FILE_NAME.getData() + EXTENSION_TXT.getData());
        long lastId = loadLastNumber();
        if (lastId == 0) {
            // 초기 작동 시 id = 1 주입
            lastId = id;
        } else {
            // 이어서 작동 시 저장된 lastId 에서 + 1
            ++lastId;
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(String.valueOf(lastId));
        } catch (IOException e) {
            throw new IllegalArgumentException("마지막 넘버를 저장하는데 실패하였습니다.", e);
        }
    }

    private void saveFile(WiseSayingItem item) {

        File file = new File(filePath, item.getId() + EXTENSION_JSON.getData());
        Converter converter = new Converter();
        String json = converter.singleConvertToJson(item);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(json);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일을 저장하는데 실패하였습니다.", e);
        }
    }

    public void saveAllWiseSaying() {
        File file = new File(filePath, ALL_DATA_FILE_NAME.getData() + EXTENSION_JSON.getData());
        Converter converter = new Converter();
        List<WiseSayingItem> wiseSayingItems = loadFiles();
        String jsonString = converter.multipleConvertToJson(wiseSayingItems);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            throw new IllegalArgumentException("빌드 명령 수행에 실패하였습니다.", e);
        }
    }

}

