package org.example.springthreory.aopPrac;

import org.example.springthreory.aopPrac.service.MemberService;
import org.example.springthreory.aopPrac.service.OrderService;
import org.example.springthreory.aopPrac.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        OrderService orderService = ac.getBean(OrderService.class);
        MemberService memberService = ac.getBean(MemberService.class);
        ProductService productService  = ac.getBean(ProductService.class);

        System.out.println("===== 주문 서비스 호출 =====");
        System.out.println(orderService.placeOrder("기계식 키보드"));

        System.out.println("\n===== 회원 서비스 호출 =====");
        System.out.println(memberService.register("kim"));

        System.out.println("\n===== 상품 서비스 호출 =====");
        System.out.println(productService.getProduct("A-100"));

        System.out.println("\n===== 진짜 프록시인지 확인 =====");
        System.out.println("orderService 의 실제 타입: " + orderService.getClass());

        ac.close();

    }
}
