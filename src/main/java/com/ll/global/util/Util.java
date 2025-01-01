package com.ll.global.util;

import com.ll.global.exception.DirectoryCRUDFailException;
import com.ll.global.exception.DirectoryNotFoundException;
import com.ll.global.exception.FileCRUDFailException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Util {
    public static class File {
        public static void set(String filePath, String content) {
            Path path = Paths.get(filePath);

            try {
                Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                final Path parentDir = path.getParent();
                if (parentDir != null && Files.notExists(parentDir)) {
                    try {
                        Files.createDirectories(parentDir);
                    } catch (IOException ex) {
                        throw new DirectoryCRUDFailException("디렉토리 생성에 실패하였습니다.");
                    }

                    try {
                        Files.writeString(path, content, StandardOpenOption.CREATE,
                                StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (IOException ex) {
                        throw new FileCRUDFailException("파일 생성에 실패하였습니다.");
                    }
                } else {
                    throw new FileCRUDFailException("파일 생성에 실패하였습니다.");
                }
            }
        }

        public static String get(String filePath) {
            Path path = Paths.get(filePath);

            try {
                return Files.readString(path);
            } catch (IOException e) {
                return "";
            }
        }

        public static boolean delete(String filePath) {
            final Path path = Paths.get(filePath);

            try {
                Files.walkFileTree(path, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                        Files.delete(filePath);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dirPath, IOException exc) throws IOException {
                        Files.delete(dirPath);
                        return FileVisitResult.CONTINUE;
                    }
                });

                return true;
            } catch (NoSuchFileException e) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }

        public static Stream<Path> fileRegStream(String dirPath, String reg) {
            try {
                return Files.walk(Paths.get(dirPath))
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().matches(reg));
            } catch (NoSuchFileException e) {
                return Stream.empty();
            } catch (IOException e) {
                throw new DirectoryNotFoundException("디렉터리를 찾을 수 없습니다.");
            }
        }

        public static boolean exists(String filePath) {
            if (Files.exists(Paths.get(filePath))) {
                return true;
            }

            return false;
        }
    }

    public static class Json {
        public static String toJson(Map<String, Object> map) {
            StringBuilder sb = new StringBuilder();
            String indent = "  ";
            sb.append("{\n");

            map.forEach((key, value) -> {
                sb.append(indent)
                        .append("\"" + key + "\"")
                        .append(": ");

                if (value instanceof String) {
                    value = "\"" + value + "\"";
                }

                sb.append(value)
                        .append(",\n");
            });

            if (!map.isEmpty()) {
                sb.delete(sb.length() - 2, sb.length());
                sb.append("\n}");
            } else {
                sb.append("}");
            }

            return sb.toString();
        }

        public static Map<String, Object> toMap(String json) {
            Map<String, Object> map = new LinkedHashMap<>();

            String indent = "  ";
            json = json.substring(1, json.length() - 1);
            String[] jsonBits = json.split(",\n" + indent);

            for (String jsonBit : jsonBits) {
                jsonBit = jsonBit.trim();

                String[] jsonFields = jsonBit.split("\": ", 2);
                String key = jsonFields[0].substring(1);
                String value = jsonFields[1];
                boolean isValueString = value.startsWith("\"") && value.endsWith("\"");

                if (isValueString) {
                    map.put(key, value.substring(1, value.length() - 1));
                } else if (value.equals("true") || value.equals("false")) {
                    map.put(key, value.equals("true"));
                } else if (value.contains(".")) {
                    map.put(key, Double.parseDouble(value));
                } else {
                    map.put(key, Long.parseLong(value));
                }
            }

            return map;
        }
    }
}
