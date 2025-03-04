package com.pathfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
/*  매칭 과정에서 @JsonProperty("길명") private String name; 등의 어노테이션이
* "길명", "총길이", "총소요시간" 등의 필드를 Path 클래스와 매칭하여 개별 객체를 생성한다. */

public class Path {
    @JsonProperty("길명")
    private String name;

    @JsonProperty("총길이")
    private String distance; // 문자열로 변경하여 파싱 후 변환

    @JsonProperty("총소요시간")
    private String duration; // "1시간 10분" 같은 문자열 형식

    @JsonProperty("시작지점명")
    private String startPoint;

    @JsonProperty("종료지점명")
    private String endPoint;

    @JsonProperty("경로정보")
    private String routeInfo;

    @JsonProperty("길소개")
    private String description; // 길 소개 추가

    // 기본 생성자 (Jackson 사용 시 필요)
    public Path() {}

    // 생성자
    public Path(String name, String distance, String duration, String startPoint, String endPoint, String routeInfo, String description) {
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.routeInfo = routeInfo;
        this.description = description;
    }

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getStartPoint() { return startPoint; }
    public void setStartPoint(String startPoint) { this.startPoint = startPoint; }

    public String getEndPoint() { return endPoint; }
    public void setEndPoint(String endPoint) { this.endPoint = endPoint; }

    public String getRouteInfo() { return routeInfo; }
    public void setRouteInfo(String routeInfo) { this.routeInfo = routeInfo; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // 숫자 변환을 위한 추가 메서드
    public double getDistanceAsDouble() {
        try {
            return Double.parseDouble(distance.replaceAll("[^0-9.]", "")); // 숫자와 점만 남김
        } catch (NumberFormatException e) {
            return 0.0; // 변환 실패 시 기본값 반환
        }
    }

    // toString() 오버라이딩 (디버깅 및 출력용)
    @Override
    public String toString() {
        return String.format("길명: %s, 거리: %s km, 소요시간: %s, 시작지점: %s, 종료지점: %s, 길소개: %s",
                name, distance, duration, startPoint, endPoint, description);
    }
}
