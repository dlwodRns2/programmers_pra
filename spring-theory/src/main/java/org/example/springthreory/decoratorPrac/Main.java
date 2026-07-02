package org.example.springthreory.decoratorPrac;

public class Main {
    public static void main(String[] args) {

        System.out.println("=====part A=====");
        new NotificationService(new EmailNotificationSender()).notifyUser("kim","hihi");
        new NotificationService(new SmsNotificationSender()).notifyUser("kim","hihi");
        new NotificationService(new KakaoNotificationSender()).notifyUser("kim","hihi");

        System.out.println("=====partB=====");
        //B1. 로깅 데코레이터
        new NotificationService(new LoggingNotificationSender(new EmailNotificationSender())).notifyUser("kim","hihi");
        //B2. 재시도 데코레이터
        new NotificationService(new RetryNotificationSender(new FlakyEmailSender())).notifyUser("kim","hihi");
        //B3. 시간측정 데코레이터
        new NotificationService(new TimingNotificationSender(new EmailNotificationSender())).notifyUser("kim","hihi");

        System.out.println("=====partC=====");
        //C1. 예제 조합 실행
        System.out.println("===C1===");
        NotificationSender sender =
                new TimingNotificationSender(       // ③ 가장 바깥: 전체 소요 시간 측정
                        new LoggingNotificationSender(  // ② 로그 남기고
                                new RetryNotificationSender(// ① 실패하면 재시도하며
                                        new FlakyEmailSender())));// (실제 발송 대상)

        new NotificationService(sender).notifyUser("user@test.com", "안녕하세요");

        //C2. 예제 조합의 순서 변경 후 실행
        System.out.println("===C2===");
        NotificationSender sender2 =
                new TimingNotificationSender(       // ③ 가장 바깥: 전체 소요 시간 측정
                        new RetryNotificationSender(  // ② 로그 남기고
                                new LoggingNotificationSender(// ① 실패하면 재시도하며
                                        new FlakyEmailSender())));// (실제 발송 대상)

        new NotificationService(sender2).notifyUser("user@test.com", "안녕하세요");

        //C1, C2 결과 비교
        //        ===C1===
        //                발송 시작 : user@test.com
        //        재시도 중... 일시적 네트워크 오류 (시도 1)
        //        재시도 중... 일시적 네트워크 오류 (시도 2)
        //                [EMAIL] (시도 3 성공) to=user@test.com : 안녕하세요
        //        발송 완료
        //        시간 측정 : 130208ns

        //                ===C2===
        //                발송 시작 : user@test.com
        //        재시도 중... 일시적 네트워크 오류 (시도 1)
        //        발송 시작 : user@test.com
        //        재시도 중... 일시적 네트워크 오류 (시도 2)
        //        발송 시작 : user@test.com
        //[EMAIL] (시도 3 성공) to=user@test.com : 안녕하세요
        //        발송 완료
        //        시간 측정 : 119125ns

    }
}
