package com.pathfinder.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pathfinder.domain.Path;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser {
    private static final String FILE_PATH = "src/main/resources/routes.json";

    public static List<Path> parseJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 파일을 읽고 "records" 배열을 파싱
            // JSON 파일을 JsonNode 객체로 변환하고 get 메서드로 recores 배열만 가져오기
            JsonNode rootNode = objectMapper.readTree(new File(FILE_PATH));
            JsonNode recordsNode = rootNode.get("records");  // "records" 배열 가져오기

            if (recordsNode != null && recordsNode.isArray()) {
                // "records" 데이터를 List<Path> 형태로 변환
                // readValue 메서드는 JSON 데이터를 Java 객체 형태로 변환한다.
                //new TypeReference<List<Path>>() {} 를 사용하여 JSON 데이터를 List<Path> 형태로 변환
                return objectMapper.readValue(recordsNode.toString(), new TypeReference<List<Path>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Path> paths = parseJson();
        if (paths != null) {
            System.out.println("총 " + paths.size() + "개의 길 정보가 로드되었습니다.");
            System.out.println("첫 번째 길: " + paths.get(1));
        } else {
            System.out.println("JSON 파싱 실패");
        }
    }
}
