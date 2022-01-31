package com.arthurhan.productapi.interceptor;

import com.arthurhan.productapi.jwt.service.JwtService;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor
{
    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (isOptions(request))
        {
            return true;
        }

        String authorization = request.getHeader("Authorization");
        jwtService.validateAuthorization(authorization);

        return true;
    }

    private boolean isOptions(HttpServletRequest request)
    {
        return Request.HttpMethod.OPTIONS.name().equals(request.getMethod());
    }
}
