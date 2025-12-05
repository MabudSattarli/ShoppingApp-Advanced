package com.example.shoppingapp.aop;


import com.example.shoppingapp.annotation.ExecutionTimeAlert;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAlertAspect {
    private final MeterRegistry meterRegistry;
    public ExecutionTimeAlertAspect(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
    }
    @Around("@annotation(executionTimeAlert)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, ExecutionTimeAlert executionTimeAlert) throws Throwable{
       long startTime = System.currentTimeMillis();
       Object result = joinPoint.proceed();
       long endTime = System.currentTimeMillis();
       long duration = endTime - startTime;
       if(duration > executionTimeAlert.threshold()){
           System.err.println("\n⚠️ [PERFORMANCE ALERT] Metod: " + joinPoint.getSignature().getName()
                   + " | Vaxt: " + duration + "ms"
                   + " | Limit: " + executionTimeAlert.threshold() + "ms\n");
           meterRegistry.counter("slow_method_execution", "method", joinPoint.getSignature().getName()).increment();
       }

       return result;
    }
}
