package com.dooho.board.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

//jwt : 전자 서명이 된 토큰
//json 형태로 구성된 토큰
// {header}.{payload}.{signature}

// header : type (해당 토큰의 타입), alg (토큰을 서명하기위해 사용된 해시 알고리즘)
// payload : sub (해당 토큰의 주인), iat (토큰이 발행된 시간), exp(토큰이 만료되는 시간)
@Service
public class TokenProvider {
    //Jwt 생성 및 검증을 위한 키
    private static final String SECURITY_KEY = "wbifpqwhjfmj!@!";
    //jwt 생성하는 메서드
    public String createAccessToken(String userEmail){
        Date exprTime = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        //jwt 생성
        return Jwts.builder()
                //암호화에 사용되는 알고리즘, 키
                .signWith(SignatureAlgorithm.HS512,SECURITY_KEY)
                //jwt 제목, 생성일, 만료일 설정
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();
    }

    public String createAccessTokenFromRefreshToken(String refreshToken) {
        try {
            // Refresh Token의 유효성을 검증
            Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(refreshToken).getBody();

            // Refresh Token의 주인 (userEmail) 가져오기
            String userEmail = claims.getSubject();

            // 여기에서 필요한 추가 로직을 구현하여 새로운 Access Token을 생성하고 반환
            // 예를 들어, 데이터베이스에서 해당 유저의 정보를 조회하거나, 다른 정보를 기반으로 Access Token을 생성할 수 있습니다.
            String newAccessToken = createAccessToken(userEmail);
            return newAccessToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createRefreshToken(String userEmail) {
        Date exprTime = Date.from(Instant.now().plus(7,ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECURITY_KEY)
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();

    }

    //jwt 검증
    public String validate(String token){
        Claims claims = null;
        try {
            //token을 키를 사용해서 디코딩
             claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        }catch (Exception e) {
            // 토큰 검증에 실패한 경우 또는 토큰이 만료된 경우 예외가 발생할 수 있습니다.
            // 검증에 실패하면 null을 반환하거나 예외를 처리하면 됩니다.
            e.printStackTrace();
            return null;
        }
        //디코딩된 payload에서 제목을 가져옴
        return claims.getSubject();
    }
}
