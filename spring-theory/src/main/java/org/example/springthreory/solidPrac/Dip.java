package org.example.springthreory.solidPrac;
interface Sender{
    void send(String msg);
}
class EmailSender implements Sender{

    @Override
    public void send(String msg) {
        System.out.println(msg+"라고 이메일로 보냄");
    }
}
class SmsSender implements Sender{

    @Override
    public void send(String msg) {
        System.out.println(msg+"라고 문자로 보냄");
    }
}
class NotificationService{
    private Sender sender;

    public NotificationService(Sender sender) {
        this.sender = sender;
    }

    void notifyUser(String msg){
        sender.send(msg);
    }
}
public class Dip {
    public static void main(String[] args) {
        NotificationService ns = new NotificationService(new EmailSender());
        NotificationService ns2 = new NotificationService(new SmsSender());

        ns.notifyUser("hihi");
        ns2.notifyUser("hihi");
    }
}
