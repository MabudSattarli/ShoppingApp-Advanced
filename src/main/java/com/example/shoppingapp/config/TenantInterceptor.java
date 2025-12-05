package com.example.shoppingapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private final TenantContext tenantContext;

    public TenantInterceptor(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. Header-dən ID-ni oxu
        String tenantId = request.getHeader("X-Tenant-ID");

        // 2. Yoxla
        if (tenantId == null || tenantId.isEmpty()) {

            tenantId = "default";
        }

        // 3. Karta yaz (Request Scope olduğu üçün bu yalnız indiki sorğuya aiddir)
        tenantContext.setTenantId(tenantId);

        System.out.println(">>> [TENANT] Sorğu gəldi. Tenant ID: " + tenantId);

        return true;
    }
}