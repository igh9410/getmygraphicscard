package com.getmygraphicscard.subscriptionservice.interceptor;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {


    private ThreadLocal<String> userEmail = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) { // Check if Authorization header is empty or not starts with "Bearer "...
            log.info("No Authorization header in the request, blocking the request...");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String jwtToken = bearerToken.substring(7);
            DecodedJWT decodedJWT;

            try {

                Algorithm algorithm = Algorithm.RSA256(getPublicKey(), getPrivateKey());
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("self")
                        .build();

                decodedJWT = verifier.verify(jwtToken);

                Claim email = decodedJWT.getClaim("email");
                userEmail.set(email.asString());
                log.info("Token = " + bearerToken);
                log.info("Extracted User Email = " + getUserEmail());

            } catch (JWTVerificationException exception){
                // Invalid signature/claims
                log.info("Invalid JWT token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false; // Block the request
            }
        }
        return true;
    }
    public RSAPublicKey getPublicKey() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("certs/public.pem").toURI());
        String publicKeyContent = new String(Files.readAllBytes(path));
        publicKeyContent = publicKeyContent
                .replaceAll("\\n", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "");

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public RSAPrivateKey getPrivateKey() throws Exception {
        Path path = Paths.get(getClass().getClassLoader().getResource("certs/private.pem").toURI());
        String privateKeyContent = new String(Files.readAllBytes(path));
        privateKeyContent = privateKeyContent
                .replaceAll("\\n", "")
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "");

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
    public String getUserEmail() {
        return userEmail.get();
    }
}
