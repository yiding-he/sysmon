package com.hyd.sysmon.manager.mvc;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!request.getRequestURI().contains("/admin/")) {
            return true;
        }

        String host = request.getHeader("Host");
        return host.equals("localhost") || host.startsWith("localhost:");
    }
}
