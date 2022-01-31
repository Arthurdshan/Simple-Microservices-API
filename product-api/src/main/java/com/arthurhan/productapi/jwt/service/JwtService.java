package com.arthurhan.productapi.jwt.service;

import com.arthurhan.productapi.exception.ValidationException;
import com.arthurhan.productapi.jwt.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService
{
    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token)
    {
        String accessToken = extractToken(token);
        try
        {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            JwtResponse user = JwtResponse.getUser(claims);

            if (user == null || user.getId() == null)
            {
                throw new ValidationException("User is not valid");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            throw new ValidationException("Error while trying to process the access token");
        }
    }

    private String extractToken(String token)
    {
        if (token == null)
        {
            throw new ValidationException("Token must be informed");
        }

        if (token.contains(" "))
        {
            return token.replace("Bearer ", Strings.EMPTY).trim();
        }
        return token;
    }
}
