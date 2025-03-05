package com.pathfinder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathfinder.domain.Path;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {
    /* JSON이 저장된 위치 고정 */
    private static final String FILE_PATH = "src/main/resources/routes.json";

    /* 싱글톤 생성 패턴 */
    private static JsonParser instance; // 싱글톤 인스턴스

    /* 데이터 캐싱 */
    private List<Path> paths; // 캐싱된 JSON 데이터

    // ObjectMapper는 불변 객체이므로 재사용 가능
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /* loadPaths() 메서드를 호출하여 List<PATH> 생성.
    * 중요한 것은 해당 생성자는 외부에서 호출되면 안되기 때문에 private로 선언
    * 오직 getInstance에서만 instance 필드에 주소값이 없을때만 호출(로드 시점) */
    private JsonParser() {
        this.paths = loadPaths(); // JSON 데이터 로드
    }

    /* instance 필드에 주소값이 없을 때만(초기 로드 시점) 인스턴스 생성*/
    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }

    /* JSON 파일을 파싱하여 List<Path>로 변환 */
    private List<Path> loadPaths() {
        try {
            JsonNode rootNode = objectMapper.readTree(new File(FILE_PATH));
            JsonNode recordsNode = rootNode.get("records");

            if (recordsNode != null && recordsNode.isArray()) {
                return objectMapper.readValue(recordsNode.toString(), new TypeReference<List<Path>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* 캐싱된 길 목록을 반환 */
    public List<Path> getPaths() {
        return paths;
    }
}