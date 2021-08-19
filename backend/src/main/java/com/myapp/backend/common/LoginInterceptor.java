package com.myapp.backend.common;

import com.myapp.backend.service.JwtService;
import com.myapp.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String ACCESS_TOKEN_HEADER = "access-token";
    private static final String REFRESH_TOKEN_HEADER = "refresh-token";
    private static final int accessExpTime = 5;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("POST")) return true;
        if (request.getMethod().equals("OPTIONS")) return true;

        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);

        if (refreshToken != null) {
            if (jwtService.validate(refreshToken)){
                String userId = jwtService.decodeUserId(accessToken);

                String refreshTokenInDB = loginService.getRefreshToken(userId);
                if(refreshTokenInDB != null && refreshTokenInDB.equals(refreshToken)){
                    String newAccessToken = jwtService.createToken(accessExpTime, "userId", userId, "user");

                    response.setHeader("jwt-access-token", newAccessToken);
                    return true;
                } else {
                    throw new IllegalArgumentException("Refresh Token Mismatch Error");
                }
            } else {
                throw new IllegalArgumentException("Refresh Token Validation Error");
            }
        } else {
            if (accessToken != null && accessToken.length() > 0) {
                if (jwtService.validate(accessToken)) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Access Token Validation Error");
                }
            } else {
                throw new IllegalArgumentException("Access Token is Expired");
            }
        }
    }
}
