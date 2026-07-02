package org.example.springthreory;

public class TimingNotificationSender implements NotificationSender{
    private NotificationSender sender;

    public TimingNotificationSender(NotificationSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String to, String message) {
        long st = System.nanoTime();
        sender.send(to,message);
        long ed = System.nanoTime();
        System.out.println("시간 측정 : " + (ed-st)+"ns");

    }
}
