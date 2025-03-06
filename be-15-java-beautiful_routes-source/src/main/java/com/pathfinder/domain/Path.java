/**
 * Path 클래스는 길 정보 데이터를 저장하는 모델 클래스입니다.
 * JSON 데이터와 매핑되며, 길의 이름, 거리, 소요시간, 시작/종료 지점 등의 정보를 포함합니다.
 *
 * 주요 기능:
 * - 길명, 거리, 소요시간, 시작/종료 지점 등의 정보 저장
 * - 문자열 거리 값을 double 형으로 변환 (`getDistanceAsDouble()`)
 * - 길 정보 출력 (`toString()`)
 *
 * @author 박성용
 * @since 2024-03-06
 */
package com.pathfinder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Path {
    /** 길의 이름 */
    @JsonProperty("길명")
    private String name;

    /** 길의 총 길이 (문자열로 저장 후 필요 시 변환) */
    @JsonProperty("총길이")
    private String distance;

    /** 길의 총 소요 시간 (예: "1시간 10분") */
    @JsonProperty("총소요시간")
    private String duration;

    /** 시작 지점의 명칭 */
    @JsonProperty("시작지점명")
    private String startPoint;

    /** 종료 지점의 명칭 */
    @JsonProperty("종료지점명")
    private String endPoint;

    /** 경로 정보 (길의 주요 경로를 설명) */
    @JsonProperty("경로정보")
    private String routeInfo;

    /** 길에 대한 설명 (부가적인 정보 제공) */
    @JsonProperty("길소개")
    private String description;

    /** 기본 생성자 (Jackson 사용 시 필요) */
    public Path() {}

    /**
     * 길 정보를 저장하는 객체 생성자입니다.
     * @param name 길의 이름
     * @param distance 길의 거리 (문자열)
     * @param duration 총 소요 시간 (문자열)
     * @param startPoint 시작 지점의 명칭
     * @param endPoint 종료 지점의 명칭
     * @param routeInfo 경로 정보
     * @param description 길에 대한 설명
     */
    public Path(String name, String distance, String duration, String startPoint, String endPoint, String routeInfo, String description) {
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.routeInfo = routeInfo;
        this.description = description;
    }

    /** Getter & Setter */
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

    /**
     * 문자열로 저장된 거리 데이터를 double 타입으로 변환하여 반환합니다.
     * 숫자와 점(.)만 남기고 변환하며, 변환 실패 시 기본값(0.0)을 반환합니다.
     * @return 변환된 거리 값 (double)
     */
    public double getDistanceAsDouble() {
        try {
            return Double.parseDouble(distance.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * 길 정보를 보기 쉽게 문자열로 반환하는 toString() 메서드입니다.
     * @return 길 정보 문자열
     */
    @Override
    public String toString() {
        return String.format("길명: %s, 거리: %s km, 소요시간: %s, 시작지점: %s, 종료지점: %s, 길소개: %s",
                name, distance, duration, startPoint, endPoint, description);
    }
}
