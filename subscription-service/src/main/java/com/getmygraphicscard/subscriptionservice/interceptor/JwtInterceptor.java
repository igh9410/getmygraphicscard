package com.getmygraphicscard.subscriptionservice.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtDecoder jwtDecoder;
    private ThreadLocal<String> userEmail = new ThreadLocal<>();

    public JwtInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwtToken = bearerToken.substring(7);
            Jwt jwt = jwtDecoder.decode(jwtToken);
            String email = jwt.getClaimAsString("sub");
            userEmail.set(email);
            System.out.println("Extracted User Email = " + userEmail);
        }
        return true;
    }

    public String getUserEmail() {
        return userEmail.get();
    }
}
