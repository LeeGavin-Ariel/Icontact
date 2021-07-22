package com.myapp.backend.service;

import com.google.gson.Gson;
import com.myapp.backend.domain.login.JwtPayloadDto;
import com.myapp.backend.domain.login.TokenDto;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "aalsdfqwelnolasdfjoasdl";
    private static final int accessExpTime = 5;
    private static final int refreshExpTime = 1800;

    private byte[] generateSecretKeyBytes() {
        return SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    }

    public <T> TokenDto create(String key, T data, String subject){

        String accessToken = createToken(accessExpTime, key, data, subject);
        String refreshToken = createToken(refreshExpTime, key, data, subject);

        return new TokenDto(accessToken, refreshToken);
    }

    public <T> String createToken(Integer expTime, String key, T data, String subject) {

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * expTime))
                .setIssuedAt(new Date())
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateSecretKeyBytes())
                .compact();
    }

    public boolean validate(String token){

        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(this.generateSecretKeyBytes());
            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String decodeUserId(String token) {

        String payload = token.split("\\.")[1];
        byte[] decodeBytes = Base64.getDecoder().decode(payload);

        try {
            String docodedString = new String(decodeBytes, StandardCharsets.UTF_8);

            JwtPayloadDto jwtPayloadDto = new Gson().fromJson(docodedString, JwtPayloadDto.class);
            System.out.println("Decoded User ID : " + jwtPayloadDto.getUserId());

            return jwtPayloadDto.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
