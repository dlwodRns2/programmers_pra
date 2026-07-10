package org.example.boardprac.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//AOP(@Aspect) vs RestControllerAdvice
//1. AOP : 로깅, 실행시간 측정 등 아무데나 끼우는 범용 작업에 대해서
//2. @RestControllerAdvice : 예외를 알맞은 HTTP 응답(404/409...)
// 로 바꾸는 웹 특화 작업에 대해서
@Aspect
@Component
public class LogginAspect {
    //해당 경로 안의 모든 메서드에 대해서 적용하겠다
    @Pointcut("execution(* org.example.boardprac.controller..*(..))")
    public void controllerLayer(){}

    @Around("controllerLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = joinPoint.getSignature().toString();
        long start = System.currentTimeMillis();
        System.out.println("===> 시작: "+name);

        try{
            Object result = joinPoint.proceed();
            return result;

        }finally {
            long took = System.currentTimeMillis();
            System.out.println("<=== 종료: "+name+" (" + took + "ms)");
        }
    }
}
