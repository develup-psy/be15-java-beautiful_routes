/**
 * PathService 클래스는 길 정보 검색 및 추천과 관련된 비즈니스 로직을 담당합니다.
 * PathRepository를 활용하여 데이터를 조회하고 가공하는 역할을 수행합니다.
 *
 * 주요 기능:
 * - 페이지별 길 정보 조회 (`getPathsByPage()`)
 * - 특정 길 검색 (`findPathByName()`)
 * - 특정 거리 이하의 길 검색 (`findPathsByMaxDistance()`)
 * - 특정 지역에서 시작하는 길 검색 (`findPathsByStartPoint()`)
 * - 맞춤형 길 추천 (`recommendPaths()`)
 *
 * @author 박성용
 * @since 2024-03-06
 */

package com.pathfinder.service;

import com.pathfinder.domain.Path;
import com.pathfinder.persistence.PathRepository;

import java.util.List;

public class PathService {
    private final PathRepository pathRepository;

    /**
     * PathService 생성자: PathRepository를 초기화합니다.
     */
    public PathService() {
        this.pathRepository = new PathRepository();
    }

    /**
     * 페이지별 길 정보를 조회합니다. (1페이지당 20개)
     * @param page 요청한 페이지 번호 (1부터 시작)
     * @return 해당 페이지의 길 목록
     */
    public List<Path> getPathsByPage(int page) {
        return pathRepository.getPathsByPage(page);
    }

    /**
     * 특정 길 이름으로 검색합니다.
     * @param name 검색할 길 이름
     * @return 해당 길 객체, 없으면 null 반환
     */
    public Path findPathByName(String name) {
        return pathRepository.getPathByName(name);
    }

    /**
     * 특정 거리 이하의 길을 검색합니다.
     * @param maxDistance 최대 거리 (km)
     * @return 해당 거리 이하의 길 목록
     */
    public List<Path> findPathsByMaxDistance(double maxDistance) {
        return pathRepository.getPathsByMaxDistance(maxDistance);
    }

    /**
     * 특정 지역에서 시작하는 길을 검색합니다.
     * @param startPoint 시작 지점 이름
     * @return 해당 지역에서 시작하는 길 목록
     */
    public List<Path> findPathsByStartPoint(String startPoint) {
        return pathRepository.getPathsByStartPoint(startPoint);
    }

    /**
     * 맞춤형 길 추천 (거리와 소요시간을 기준으로 필터링)
     * @param minDistance 최소 거리 (km)
     * @param maxDistance 최대 거리 (km)
     * @param maxDuration 최대 소요 시간 (시간 단위)
     * @return 추천된 길 목록
     */
    public List<Path> recommendPaths(double minDistance, double maxDistance, int maxDuration) {
        return pathRepository.getAllPaths().stream()
                .filter(path -> path.getDistanceAsDouble() >= minDistance && path.getDistanceAsDouble() <= maxDistance)
                .filter(path -> parseDurationToHours(path.getDuration()) <= maxDuration)
                .toList();
    }

    /**
     * 소요 시간을 "시간" 단위로 변환하는 메서드 (예: "1시간 30분" → 1.5)
     * @param duration 소요 시간 문자열
     * @return 시간 단위 변환 값
     */
    private double parseDurationToHours(String duration) {
        if (duration == null || duration.isEmpty()) {
            return Double.MAX_VALUE; // 잘못된 데이터 방어 코드
        }
        try {
            String[] parts = duration.split(" ");
            double hours = 0;
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].contains("시간")) {
                    hours += Double.parseDouble(parts[i].replace("시간", ""));
                } else if (parts[i].contains("분")) {
                    hours += Double.parseDouble(parts[i].replace("분", "")) / 60;
                }
            }
            return hours;
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE; // 잘못된 형식일 경우 방어 코드
        }
    }
}
