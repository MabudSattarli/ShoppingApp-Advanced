package com.example.shoppingapp.aop;


import com.example.shoppingapp.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class CashingAspect {

    private final ConcurrentHashMap<Long, User> userCache = new ConcurrentHashMap<>();

@Around("execution(* com.example.shoppingapp.service.UserService.getUserById(..))")
  public Object cacheUser(ProceedingJoinPoint joinPoint) throws Throwable {
    Long userId = (Long) joinPoint.getArgs()[0];
    if(userCache.containsKey(userId)){
        System.out.println("cache hit. " +  userId + "cache`den alindi");
        return userCache.get(userId);
    }
    User result = (User) joinPoint.proceed();
    userCache.put(userId, result);
    System.out.println("istifadeci " + userId + "kese elave edildi");
    return result;
}
}
