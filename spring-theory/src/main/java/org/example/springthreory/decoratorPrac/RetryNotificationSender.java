package org.example.springthreory.decoratorPrac;

public class RetryNotificationSender implements NotificationSender{
    private NotificationSender sender;
    public RetryNotificationSender(NotificationSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String to, String message) {

        for(int i=0;i<3;i++){
            try{
                sender.send(to,message);
                return;
            }catch (RuntimeException e){
                System.out.println("재시도 중... "+e.getMessage());
            }
        }
        throw new RuntimeException("3번 모두 실패");
    }
}
