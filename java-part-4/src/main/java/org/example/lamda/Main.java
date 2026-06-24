package org.example.lamda;


import java.util.ArrayList;

public class Main {
    static void main() {
        //Step1. 익명클래스
        System.out.println("===== 1. 익명 클래스 vs 람다 (같은 동작) =====");
        Operation add = new Operation(){
            @Override
            public int apply(int a, int b) {
                return a+b;
            }
        };

        //Step2. 람다
        Operation addLamda= (a,b)->a+b;

        System.out.println("익명 클래스 add: "+add.apply(3,4));
        System.out.println("람다 add: "+addLamda.apply(3,4));

        //Step3. 람다 문법 축약 3단계 : 본문이 1줄이면 {},return 생략 가능
        System.out.println("===== 2. 람다로 만든 연산들 =====");
        Operation addLamda1 = (int a,int b)->{return a+b;};
        Operation addLamda2 = (a,b)->{return a+b;};
        Operation addLamda3 = (a,b)->a+b;

//        System.out.println("3 + 4 = "+addLamda1.apply(3,4));
//        System.out.println(addLamda2.apply(3,4));
//        System.out.println(addLamda3.apply(3,4));

        Operation sub = (a,b)->a-b;
        Operation mul =(a,b)->a*b;
        System.out.println("3 + 4 = "+addLamda1.apply(3,4));
        System.out.println("9 - 2 = "+sub.apply(9,2));
        System.out.println("3 * 5 = "+mul.apply(3,5));

        //Step4. 매개변수 개수별 람다
        System.out.println("===== 3. 매개변수 개수별 람다 =====");
        Runnable r = ()->{
            System.out.println("(0개) 안녕하세요, 람다!");
        };
        Printer p = msg-> System.out.println("(1개) [로그] " +msg);
        Operation o = (a,b)->a+b;

        r.run();
        p.print("hihi");
        System.out.println("(2개) 10 + 20 = " +o.apply(1,2));

        System.out.println("===== 4. 실전: Comparator로 길이순 정렬 =====");
        ArrayList<String> list = new ArrayList<>();
        list.add("가나다");
        list.add("가");
        list.add("가나");

        //Step5. Comparator로 리스트 정렬, 정렬 기준을 람다로 생성
        System.out.println("정렬 전 : "+list);
        //Comparator은 함수형 인터페이스(@FunctionalInterface)
        list.sort((a,b)->{
            return a.length()-b.length();
        });
        System.out.println("정렬 후 : "+list);
    }
}
