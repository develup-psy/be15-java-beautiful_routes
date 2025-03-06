/**
 * User 클래스는 JSON 데이터를 기반으로 사용자 정보를 저장하는 모델 클래스입니다.
 *
 * 주요 기능:
 * - 사용자 ID, 이름, 이메일, 비밀번호, 즐겨찾기 목록 등의 정보 저장
 * - JSON 직렬화/역직렬화 지원 (`@JsonProperty` 적용)
 * - 사용자 정보 출력 (`toString()`)
 *
 * @author 박성용
 * @since 2024-03-06
 */

package com.pathfinder.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("age")
    private int age;

    @JsonProperty("favorites")
    private List<String> favorites; // 즐겨찾기 목록 (저장된 길 ID 리스트)

    /**
     * 기본 생성자 (Jackson 사용 시 필요)
     */
    public User() {}

    /**
     * 사용자 객체를 생성하는 생성자입니다.
     * @param id 사용자 ID
     * @param name 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @param age 사용자 나이
     * @param favorites 즐겨찾기 목록 (저장된 길 ID 리스트)
     */
    public User(String id, String name, String email, String password, int age, List<String> favorites) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.favorites = favorites;
    }

    /** Getter & Setter */
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public List<String> getFavorites() { return favorites; }
    public void setFavorites(List<String> favorites) { this.favorites = favorites; }

    /**
     * 사용자 정보를 보기 쉽게 출력하는 toString() 메서드
     * @return 사용자 정보 문자열
     */
    @Override
    public String toString() {
        return String.format("User [ID: %s, Name: %s, Email: %s, Age: %d, Favorites: %s]",
                id, name, email, age, favorites);
    }
}

