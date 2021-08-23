package com.sbbetting.training.security.token;

import com.sbbetting.training.error.TokenError;
import com.sbbetting.training.exception.ServiceException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TokenParser {

    private static final Logger LOG = LoggerFactory.getLogger(TokenParser.class);

    @Value("${security.jwt.secret.key}")
    private String secretKey;

    public String getUserId(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            LOG.trace("Error during parsing jwt token, Reason : {}", e.getMessage(), e);
            throw new ServiceException(TokenError.INVALID_TOKEN_ERROR);
        }
    }

    public Date getExpirationDate(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } catch (Exception e) {
            LOG.trace("Error during parsing jwt token, Reason : {}", e.getMessage(), e);
            throw new ServiceException(TokenError.INVALID_TOKEN_ERROR);
        }
    }

    public String createToken(String userId, int duration, TimeUnit unit) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + unit.toMillis(duration);
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
