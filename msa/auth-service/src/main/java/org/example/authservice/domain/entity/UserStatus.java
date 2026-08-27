package org.example.authservice.domain.entity;

//회원 계정 상태 관리 : saga 패턴에서 사용
public enum UserStatus {
    ACTIVE, //정상
    WITHDRAWING, //탈퇴 중
    WITHDRAW, //탈퇴 확정
}
