package com.example.shoppingapp.aop;

import com.example.shoppingapp.annotation.RateLimit;
import com.example.shoppingapp.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {
    private final ConcurrentHashMap<String, List<Long>> requestCounts = new ConcurrentHashMap<>();
    @Before("@annotation(rateLimit)")
    public void checkRateLimit(JoinPoint joinPoint, RateLimit rateLimit){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientIp = request.getRemoteAddr(); // Real layihədə "X-Forwarded-For" header-nə baxılır


        long currentTime = System.currentTimeMillis();


        requestCounts.putIfAbsent(clientIp, new ArrayList<>());
        List<Long> timestamps = requestCounts.get(clientIp);

        synchronized (timestamps) {
            long windowStart = currentTime - (rateLimit.duration() * 1000L);


            timestamps.removeIf(t -> t < windowStart);

            // 6. Limit yoxlanışı
            if (timestamps.size() >= rateLimit.limit()) {
                throw new RateLimitExceededException("Çox sayda sorğu! Zəhmət olmasa " + rateLimit.duration() + " saniyə gözləyin.");
            }


            timestamps.add(currentTime);
        }
    }
}