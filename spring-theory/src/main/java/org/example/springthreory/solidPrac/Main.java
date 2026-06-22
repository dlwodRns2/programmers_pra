package org.example.springthreory.solidPrac;

public class Main {
    public static void main(String[] args) {
        //SRP
        System.out.println("===== SRP : 단일 책임 =====");
        Journal j = new Journal();
        JournalSaver js = new JournalSaver();
        j.add("오늘은 자바를 배웠다"); j.add("SOLID는 어렵지만 재밌다");
        js.print(j);

        //OCP
        System.out.println("\n===== OCP : 개방-폐쇄 =====");
        DiscountPolicy[] policies = { new BasicDiscount(), new GoldDiscount(), new VipDiscount() };
        String[] names = { "일반", "골드", "VIP" };
        for(int i=0;i<policies.length;i++){
            System.out.println(names[i] + " 회원 -> " + policies[i].discount(10000) + "원");
        }

        //LSP
        System.out.println("\n===== LSP : 리스코프 치환 =====");
        Bird[] birds ={ new Sparrow(), new Penguin() };
        for(Bird b : birds){
            b.eat();
        }
        //ISP
        System.out.println("\n===== ISP : 인터페이스 분리 =====");
        Printer p = new SimplePrinter();
        p.print();

        SmartMachine sm = new SmartMachine();
        sm.print();
        sm.scan();

        //DIP
        System.out.println("\n===== DIP : 의존관계 역전 =====");
        new NotificationService(new EmailSender()).notifyUser("주문이 완료되었습니다");
        new NotificationService(new SmsSender()).notifyUser("주문이 완료되었습니다");
    }
}
