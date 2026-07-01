package org.example.springthreory.aopPrac;

import org.example.springthreory.aopPrac.service.*;
import org.example.springthreory.testPrac.ProductDao;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public DefaultAdvisorAutoProxyCreator autoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }
    //Advisor = advice + pointcut
    @Bean public Advisor performanceAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.example.springthreory.aopPrac.service..*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, new PerformanceMonitorAdvice());
    }
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl();
    }
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl();
    }
    @Bean
    public ProductService productService(){
        return new ProductServiceImpl();
    }
    @Bean
    public ProductDao productDao(){
        return new ProductDao();
    }
}
