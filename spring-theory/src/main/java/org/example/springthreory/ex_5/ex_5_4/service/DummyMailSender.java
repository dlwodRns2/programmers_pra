package org.example.springthreory.ex_5.ex_5_4.service;

//* 아무것도 하지 않는 더미
//메일 발송이 핵심이 아닌 상황(개발/테스트 하는 상황)에서 사용
//운영환경에서는 JavaMail 등으로 실제 발송하는 구현을 끼우면 됨.(코드 변경 없이 설정만으로)
public class DummyMailSender implements MailSender{
    @Override
    public void send(Mail mail) {

    }
}
