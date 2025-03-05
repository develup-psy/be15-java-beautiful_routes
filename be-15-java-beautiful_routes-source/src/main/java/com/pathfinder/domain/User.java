/**
 * User 클래스는 사용자 정보 데이터를 저장하는 모델 클래스입니다.
 * 사용자ID, 이름, 이메일, 비밀번호, 즐겨찾기 정보를 포함합니다.
 *
 *
 * @author 박성용
 * @version 1.0
 * @since 2024-03-06
 */
package com.pathfinder.domain;

import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String pwd;
    private List<Favorite> favorite;



}
