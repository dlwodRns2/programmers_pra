package org.example.springthreory.decoratorPrac;

public class LoggingNotificationSender implements NotificationSender{
    private final NotificationSender delegate;

    public LoggingNotificationSender(NotificationSender delegate) {
        this.delegate = delegate;
    }
    @Override
    public void send(String to, String message) {
        // TODO 1: "발송 시작" 로그 (to 포함)
        System.out.println("발송 시작 : "+to);
        // TODO 2: delegate.send(to, message);   ← 실제 발송은 위임
        delegate.send(to,message);
        // TODO 3: "발송 완료" 로그
        System.out.println("발송 완료");
    }
}
