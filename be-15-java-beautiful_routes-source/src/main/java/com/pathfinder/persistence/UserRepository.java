/**
 * UserRepository 클래스는 사용자 데이터를 JSON 파일에서 관리하는 저장소 클래스입니다.
 * JsonFilePath Enum을 사용하여 경로를 관리하고, JsonParser를 활용하여 데이터를 로드 및 저장합니다.
 *
 * 주요 기능:
 * - 모든 사용자 데이터 불러오기 (`getAllUsers()`)
 * - 특정 사용자 검색 (`getUserById()`)
 * - 사용자 추가 (`addUser()`)
 * - 사용자 삭제 (`removeUser()`)
 * - 사용자 정보 업데이트 (`updateUser()`)
 *
 * @author 박성용
 * @since 2024-03-06
 */
package com.pathfinder.persistence;

import com.pathfinder.utils.FilePath;
import com.pathfinder.domain.User;
import com.pathfinder.utils.JsonParser;
import com.pathfinder.utils.JsonWriter;

import java.util.List;

public class UserRepository {
    private final String filePath = FilePath.USERFILE.getFilePath();
    private List<User> users;

    /**
     * UserRepository 생성자: JSON 데이터를 로드하여 사용자 리스트를 초기화합니다.
     */
    public UserRepository() {
        this.users = JsonParser.parse(filePath, User.class);
    }

    /**
     * 모든 사용자 데이터를 반환합니다.
     * @return 사용자 리스트
     */
    public List<User> getAllUsers() {
        return users;
    }

    /**
     * ID를 기반으로 특정 사용자를 검색합니다.
     * @param userId 찾을 사용자 ID
     * @return 해당 ID의 사용자 객체, 없으면 null 반환
     */
    public User getUserById(String userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 새로운 사용자를 추가합니다.
     * @param user 추가할 사용자 객체
     */
    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    /**
     * 특정 사용자를 삭제합니다.
     * @param userId 삭제할 사용자 ID
     */
    public void removeUser(String userId) {
        users.removeIf(user -> user.getId().equals(userId));
        saveUsers();
    }

    /**
     * 특정 사용자의 정보를 업데이트합니다.
     * @param updatedUser 업데이트할 사용자 객체
     */
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                saveUsers();
                return;
            }
        }
    }

    /**
     * 변경된 사용자 데이터를 JSON 파일에 저장합니다.
     */
    private void saveUsers() {
        JsonWriter.write(filePath, users);
    }
}
