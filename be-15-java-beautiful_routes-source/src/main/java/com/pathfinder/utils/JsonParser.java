/**
 * JsonParser 클래스는 JSON 파일을 읽고 변환하는 역할을 담당합니다.
 * 데이터 관리는 Repository 계층에서 수행하며, 이 클래스는 파일 입출력만 처리합니다.
 *
 * @author 박성용
 * @since 2024-03-06
 */

package com.pathfinder.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonParser() {}

    /**
     * JSON 파일을 읽고, 제네릭 타입(T) 리스트로 변환합니다.
     * @param filePath JSON 파일 경로
     * @param clazz 변환할 클래스 타입
     * @return JSON 데이터에서 변환된 리스트 (List<T>), 실패 시 빈 리스트 반환
     */
    public static <T> List<T> parse(String filePath, Class<T> clazz) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                createEmptyJsonFile(filePath, file);
                return new ArrayList<>();
            } else if (file.length() == 0) {
                return new ArrayList<>(); // 파일이 비어있으면 빈 리스트 반환
            }

            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode recordsNode = rootNode.has("records") ? rootNode.get("records") : rootNode;

            if (recordsNode != null && recordsNode.isArray()) {
                TypeFactory typeFactory = objectMapper.getTypeFactory();
                CollectionType listType = typeFactory.constructCollectionType(List.class, clazz);
                return objectMapper.readValue(recordsNode.toString(), listType);
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * JSON 파일이 존재하지 않을 경우, 자동으로 생성하는 메서드입니다.
     * @param filePath 생성할 JSON 파일 경로
     */
    private static void createEmptyJsonFile(String filePath, File file) {
        try {
            file.createNewFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>()); // 빈 리스트 저장
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
