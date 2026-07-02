package org.example.springthreory.ex_5.ex_5_4;

//* 메일 서비스 추상화
//요구 사항 추가 : 레벨이 업그레이드된 사용자에게 안내 메일을 보낸다
//* 문제점
//만약 UserServiceImpl이 '직접' 메일을 보낸다면
//1. 테스트마다 진짜 메일이 나가거나,SMTP 서버가 없으면 테스트 실패
//2. 외부 환경(메일 서버))에 코드와 테스트가 묶인다
//=> UserServiceImpl이 메일 서버의 서버 주소,포트, 인증 정보 등을 다 알아야함
//=> 메일 서버 변경 시, UserServiceImpl 코드를 변경해야함.

//'메일을 보낸다'를 MailSender 인터페이스로 추상화하고 DI로 주입한다.
public class Start {
    public static void main(String[] args) {

    }
}
