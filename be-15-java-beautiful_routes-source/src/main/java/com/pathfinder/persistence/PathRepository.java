/**
 * PathRepository 클래스는 길(Path) 데이터를 JSON 파일에서 관리하는 저장소 클래스입니다.
 * JSON 파일을 활용하여 길 데이터를 저장, 불러오기, 검색할 수 있습니다.
 *
 * 주요 기능:
 * - 모든 길 데이터 불러오기 (`getAllPaths()`)
 * - 특정 길 검색 (`getPathByName()`)
 * - 특정 거리 이하의 길 검색 (`getPathsByMaxDistance()`)
 * - 특정 지역에서 시작하는 길 검색 (`getPathsByStartPoint()`)
 *
 * @author 박성용
 * @since 2024-03-06
 */

package com.pathfinder.persistence;

import com.pathfinder.utils.FilePath;
import com.pathfinder.domain.Path;
import com.pathfinder.utils.JsonParser;
import com.pathfinder.utils.JsonWriter;

import java.util.List;
import java.util.stream.Collectors;

public class PathRepository {
    private final String filePath = FilePath.PATHFILE.getFilePath();
    private final List<Path> paths;

    /**
     * PathRepository 생성자: JSON 데이터를 로드하여 길 리스트를 초기화합니다.
     */
    public PathRepository() {
        this.paths = JsonParser.parse(filePath);
    }

    /**
     * 모든 길 데이터를 반환합니다.
     * @return 길 리스트
     */
    public List<Path> getAllPaths() {
        return paths;
    }

    /**
     * 페이지별 길 목록 조회 (1페이지당 20개)
     * @param page 요청한 페이지 번호 (1부터 시작)
     * @return 해당 페이지의 길 목록
     */
    public List<Path> getPathsByPage(int page) {
        int pageSize = 20;
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, paths.size());

        if (fromIndex >= paths.size()) {
            return List.of(); // 요청한 페이지가 데이터 범위를 초과한 경우 빈 리스트 반환
        }
        return paths.subList(fromIndex, toIndex);
    }

    /**
     * 특정 길 이름으로 검색합니다.
     * @param name 검색할 길 이름
     * @return 해당 이름의 길 객체, 없으면 null 반환
     */
    public Path getPathByName(String name) {
        return paths.stream()
                .filter(path -> path.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 특정 거리 이하의 길을 검색합니다.
     * @param maxDistance 최대 거리 (km)
     * @return 해당 거리 이하의 길 목록
     */
    public List<Path> getPathsByMaxDistance(double maxDistance) {
        return paths.stream()
                .filter(path -> path.getDistanceAsDouble() <= maxDistance)
                .collect(Collectors.toList());
    }

    /**
     * 특정 지역에서 시작하는 길을 검색합니다.
     * @param startPoint 시작 지점 이름
     * @return 해당 지역에서 시작하는 길 목록
     */
    public List<Path> getPathsByStartPoint(String startPoint) {
        return paths.stream()
                .filter(path -> path.getStartPoint().contains(startPoint))
                .collect(Collectors.toList());
    }

    /**
     * 길 정보를 JSON 파일에 저장합니다.
     */
    public void savePaths() {
        JsonWriter.write(filePath, paths);
    }
}
