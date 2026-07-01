package org.example.springthreory.aopPrac;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jspecify.annotations.Nullable;

public class PerformanceMonitorAdvice implements MethodInterceptor {
    @Override
    public @Nullable Object invoke(MethodInvocation invocation) throws Throwable {
        String name = invocation.getMethod().getDeclaringClass().getSimpleName()
                +"."+invocation.getMethod().getName();
        long start = System.nanoTime();

        try{
            return invocation.proceed();
        }finally{
            long ms = (System.nanoTime()-start)/1_000_000;
            System.out.printf("[PERF] %s : %dms%n", name, ms);
        }
    }
}
