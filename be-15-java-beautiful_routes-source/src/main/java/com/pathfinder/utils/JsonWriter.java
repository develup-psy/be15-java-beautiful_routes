/**
 * JsonWriter 클래스는 Java 객체를 JSON 파일에 저장하는 역할을 담당합니다.
 * 데이터 저장 책임을 JsonParser와 분리하여 단일 책임 원칙(SRP)을 준수합니다.
 *
 * @author 박성용
 * @version 1.0
 * @since 2024-03-06
 */

package com.pathfinder.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonWriter() {}

    /**
     * JSON 데이터 리스트를 파일에 저장합니다.
     * @param filePath JSON 파일 경로
     * @param data 저장할 데이터 리스트
     */
    public static <T> void write(String filePath, List<T> data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
