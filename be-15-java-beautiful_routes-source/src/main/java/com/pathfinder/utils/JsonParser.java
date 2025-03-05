/**
 * JsonParser 클래스는 JSON 파일에서 데이터를 읽어 Path 객체 리스트로 변환하는 역할을 합니다.
 *
 * 주요 기능:
 * - JSON 파일을 읽어 Path 객체 리스트로 변환
 * - 싱글톤 패턴을 적용하여 한 번만 JSON 데이터를 로드하고 캐싱
 * - 캐싱된 데이터를 반환하여 불필요한 파일 읽기 방지
 *
 * @author 벅성용
 * @version 1.0
 * @since 2024-03-06
 */

package com.pathfinder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathfinder.domain.Path;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {
    /** JSON 파일 경로 (프로젝트 내 resources 디렉토리에 위치) */
    private static final String FILE_PATH = "src/main/resources/routes.json";

    /** JsonParser의 유일한 인스턴스 (싱글톤 패턴 적용) */
    private static JsonParser instance;

    /** 캐싱된 길 데이터 목록 */
    private List<Path> paths;

    /** JSON 파싱을 위한 ObjectMapper (불변 객체이므로 재사용 가능) */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * private 생성자: 외부에서 직접 인스턴스를 생성할 수 없도록 제한
     * JsonParser의 인스턴스는 getInstance() 메서드를 통해 한 번만 생성됩니다.
     */
    private JsonParser() {
        this.paths = loadPaths(); // JSON 데이터를 한 번만 로드하여 캐싱
    }

    /**
     * JsonParser의 싱글톤 인스턴스를 반환합니다.
     * @return JsonParser의 유일한 인스턴스
     */
    public static JsonParser getInstance() {
        if (instance == null) {
            instance = new JsonParser();
        }
        return instance;
    }

    /**
     * JSON 파일을 읽고 "records" 키를 기준으로 List<Path>로 변환합니다.
     * @return JSON에서 변환된 길 목록 (List<Path>), 실패 시 null 반환
     */
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

    /**
     * 캐싱된 길 목록을 반환합니다.
     * @return 파싱된 길 목록 (List<Path>)
     */
    public List<Path> getPaths() {
        return paths;
    }
}