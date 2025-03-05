package com.pathfinder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonParser 클래스는 JSON 파일을 읽고 변환하는 역할을 담당합니다.
 * 데이터 관리는 Repository 계층에서 수행하며, 이 클래스는 파일 입출력만 처리합니다.
 *
 * @author 박성용
 * @version 1.2
 * @since 2024-03-06
 */
public class JsonParser{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonParser() {}
    /**
     * JSON 파일을 읽고, 제네릭 타입(T) 리스트로 변환합니다.
     * @param filePath JSON 파일 경로
     * @return JSON 데이터에서 변환된 리스트 (List<T>), 실패 시 빈 리스트 반환
     */
    public static <T> List<T> parse(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>(); // 파일이 없거나 비어있으면 빈 리스트 반환
            }

            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode recordsNode = rootNode.get("records");

            if (recordsNode != null && recordsNode.isArray()) {
                return objectMapper.readValue(recordsNode.toString(), new TypeReference<List<T>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
