/**
 * UserService 클래스는 사용자 관리와 관련된 비즈니스 로직을 담당합니다.
 * - 회원가입 시 유효성 검사, 이메일 인증, 중복 검사 수행
 * - 추후 API에서 데이터를 받아오는 경우에도 확장 가능
 *
 * 주요 기능:
 * - 모든 사용자 조회 (`getAllUsers()`)
 * - 사용자 ID로 검색 (`getUserById()`)
 * - 사용자 등록 (`registerUser()`)
 * - 사용자 삭제 (`deleteUser()`)
 * - 사용자 정보 업데이트 (`updateUser()`)
 *
 * @author 박성용
 * @version 1.1
 * @since 2024-03-06
 */

package com.pathfinder.service;

import com.pathfinder.domain.User;
import com.pathfinder.persistence.UserRepository;

import java.util.List;
import java.util.regex.Pattern;

public class UserManage {
    private final UserRepository userRepository;

    /**
     * UserService 생성자: UserRepository를 초기화합니다.
     */
    public UserManage() {
        this.userRepository = new UserRepository();
    }

    /**
     * 모든 사용자를 조회합니다.
     * @return 사용자 리스트
     */
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    /**
     * 특정 ID를 가진 사용자를 조회합니다.
     * @param userId 찾을 사용자 ID
     * @return 해당 ID의 사용자 객체, 없으면 null 반환
     */
    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    /**
     * 새로운 사용자를 등록합니다. (이메일 인증 및 유효성 검사 포함)
     * @param user 추가할 사용자 객체
     */
    public void registerUser(User user) {
        if (!isValidUser(user)) {
            System.out.println("사용자 정보가 유효하지 않습니다.");
            return;
        }

        if (userRepository.getUserById(user.getId()) == null) {
            userRepository.addUser(user);
            System.out.println("사용자가 성공적으로 등록되었습니다.");
        } else {
            System.out.println("이미 존재하는 사용자 ID입니다.");
        }
    }

    /**
     * 특정 사용자를 삭제합니다.
     * @param userId 삭제할 사용자 ID
     */
    public void deleteUser(String userId) {
        if (userRepository.getUserById(userId) != null) {
            userRepository.removeUser(userId);
            System.out.println("사용자가 삭제되었습니다.");
        } else {
            System.out.println("존재하지 않는 사용자입니다.");
        }
    }

    /**
     * 사용자 정보를 업데이트합니다.
     * @param updatedUser 업데이트할 사용자 객체
     */
    public void updateUser(User updatedUser) {
        if (!isValidUser(updatedUser)) {
            System.out.println("입력된 사용자 정보가 유효하지 않습니다.");
            return;
        }

        if (userRepository.getUserById(updatedUser.getId()) != null) {
            userRepository.updateUser(updatedUser);
            System.out.println("사용자 정보가 업데이트되었습니다.");
        } else {
            System.out.println("존재하지 않는 사용자입니다.");
        }
    }

    /**
     * 사용자 정보 유효성 검사
     * - ID, 이름, 이메일, 비밀번호가 유효한지 확인
     * @param user 검사할 사용자 객체
     * @return 유효하면 true, 그렇지 않으면 false 반환
     */
    private boolean isValidUser(User user) {
        return isValidId(user.getId()) && isValidName(user.getName()) &&
                isValidEmail(user.getEmail()) && isValidPassword(user.getPassword());
    }

    /**
     * 사용자 ID 유효성 검사 (영문 + 숫자, 5~15자)
     */
    private boolean isValidId(String id) {
        return !id.isEmpty() && id.matches("^[a-zA-Z0-9]{2,15}$");
    }

    /**
     * 사용자 이름 유효성 검사 (한글 또는 영문 2~20자)
     */
    private boolean isValidName(String name) {
        return !name.isEmpty() && name.matches("^[가-힣a-zA-Z]{1,20}$");
    }

    /**
     * 이메일 유효성 검사
     */
    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }

    /**
     * 비밀번호 유효성 검사 (최소 8자, 대소문자 + 숫자 + 특수문자 포함)
     */
    private boolean isValidPassword(String password) {
        return !password.isEmpty() && password.matches("^(?=.*[a-z])(?=.*\\d).{8,}$");
    }
}
