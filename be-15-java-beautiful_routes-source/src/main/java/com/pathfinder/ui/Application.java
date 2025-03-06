package com.pathfinder.ui;

import com.pathfinder.domain.Path;
import com.pathfinder.domain.User;
import com.pathfinder.service.PathService;
import com.pathfinder.service.UserManage;

import java.util.List;
import java.util.Scanner;

/**
 * Application 클래스는 콘솔에서 사용자 입력을 받아 `UserManage`를 통해 로그인 또는 회원가입을 진행하며,
 * 로그인한 사용자만 `PathService`를 통해 길 정보 관리 기능을 사용할 수 있도록 합니다.
 *
 * 주요 기능:
 * - 사용자 로그인 및 회원가입 (최초 실행 시 선택 필수)
 * - 페이징된 길 목록 조회
 * - 특정 길 검색
 * - 특정 거리 이하의 길 검색
 * - 특정 지역에서 시작하는 길 검색
 * - 맞춤형 길 추천
 *
 * @author 박성용
 * @version 1.3
 * @since 2024-03-06
 */
public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManage userService = new UserManage();
    private static final PathService pathService = new PathService();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        authenticateUser(); // 로그인 또는 회원가입 필수
        while (true) {
            printMenu();
            int choice = getChoice();

            switch (choice) {
                case 1 -> listPathsByPage();
                case 2 -> findPathByName();
                case 3 -> findPathsByMaxDistance();
                case 4 -> findPathsByStartPoint();
                case 5 -> recommendPaths();
                case 0 -> exitApplication();
                default -> System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }
    }

    /**
     * 사용자 인증 (로그인 또는 회원가입 선택)
     */
    private static void authenticateUser() {
        while (loggedInUser == null) {
            System.out.println("\n==== 사용자 인증 ====");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.print("선택: ");
            int choice = getChoice();
            switch (choice) {
                case 1 -> login();
                case 2 -> registerUser();
                default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }

    /**
     * 로그인 기능 (회원만 길 관련 기능 사용 가능)
     */
    private static void login() {
        System.out.println("\n==== 로그인 ====");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();

        User user = userService.getUserById(id);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("✅ 로그인 성공: " + user.getName() + "님 환영합니다!");
        } else {
            System.out.println("❌ 로그인 실패: 잘못된 ID 또는 비밀번호입니다. 다시 시도해주세요.");
        }
    }

    /**
     * 회원가입 기능
     */
    private static void registerUser() {
        System.out.println("\n==== 회원가입 ====");
        System.out.print("ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("이름: ");
        String name = scanner.nextLine().trim();
        System.out.print("이메일: ");
        String email = scanner.nextLine().trim();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();
        System.out.println("나이 : ");
        int age = scanner.nextInt();

        User user = new User(id, name, email, password, age, List.of());
        userService.registerUser(user);
        System.out.println("✅ 회원가입이 완료되었습니다. 이제 로그인해주세요.");
    }

    private static void printMenu() {
        System.out.println("\n==== 길 정보 관리 시스템 ====");
        System.out.println("1. 길 목록 조회");
        System.out.println("2. 특정 길 검색 (이름)");
        System.out.println("3. 특정 거리 이하의 길 검색");
        System.out.println("4. 특정 지역에서 시작하는 길 검색");
        System.out.println("5. 맞춤형 길 추천");
        System.out.println("0. 종료");
        System.out.print("메뉴를 선택하세요: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listPathsByPage() {
        System.out.print("조회할 페이지 번호 입력(기본은 1페이지): ");
        int page = Integer.parseInt(scanner.nextLine().trim());
        List<Path> paths = pathService.getPathsByPage(page);
        paths.forEach(System.out::println);
    }

    private static void findPathByName() {
        System.out.print("검색할 길 이름: ");
        String name = scanner.nextLine().trim();
        Path path = pathService.findPathByName(name);
        System.out.println(path != null ? path : "해당 길이 없습니다.");
    }

    private static void findPathsByMaxDistance() {
        System.out.print("최대 거리 (km): ");
        double maxDistance = Double.parseDouble(scanner.nextLine().trim());
        List<Path> paths = pathService.findPathsByMaxDistance(maxDistance);
        paths.forEach(System.out::println);
    }

    private static void findPathsByStartPoint() {
        System.out.print("검색할 시작 지점: ");
        String startPoint = scanner.nextLine().trim();
        List<Path> paths = pathService.findPathsByStartPoint(startPoint);
        paths.forEach(System.out::println);
    }

    private static void recommendPaths() {
        System.out.print("최소 거리 (km): ");
        double minDistance = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("최대 거리 (km): ");
        double maxDistance = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("최대 소요 시간 (시간 단위): ");
        int maxDuration = Integer.parseInt(scanner.nextLine().trim());
        List<Path> paths = pathService.recommendPaths(minDistance, maxDistance, maxDuration);
        paths.forEach(System.out::println);
    }

    private static void exitApplication() {
        System.out.println("프로그램을 종료합니다.");
        scanner.close();
        System.exit(0);
    }
}
