package org.example.springthreory.decoratorPrac;

public class NotificationService {
    private NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender=sender;
    }
    public void notifyUser(String to, String message){
        sender.send(to,message);
    }
}
