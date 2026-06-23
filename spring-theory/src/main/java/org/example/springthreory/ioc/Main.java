package org.example.springthreory.ioc;

public class Main {
    public static void main(String[] args) {
        //Step2 : DI
        System.out.println("===== 2. DI =====");
        CoffeeMaker cm1 = new CoffeeMaker(new ColombiaBean());
        CoffeeMaker cm2 = new CoffeeMaker(new EthiopiaBean());
        cm1.brew();
        cm2.brew();

        //Step3: IoC 컨테이너
        System.out.println("\n===== 3. IoC 컨테이너 =====");
        CoffeeContainer cc = new CoffeeContainer();
        CoffeeMaker cm3 = cc.getCoffeeMaker();
        cm3.brew();

        //Step4 : 할리우드 원칙
        System.out.println("\n===== 4. 헐리우드 원칙 =====");
        Button button = new Button();
        button.setListener(new LikeAction()); //나는 버튼에 리스너를 끼워두기만하고,
        button.press(); //호출은 버튼이 한다
    }

}
