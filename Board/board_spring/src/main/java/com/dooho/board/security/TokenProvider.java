package com.dooho.board.security;

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
    private static final String SECURITY_KEY = "jwtseckey!@";
    //jwt 생성하는 메서드
    public String create(String userEmail){
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        //jwt 생성
        return Jwts.builder()
                //암호화에 사용되는 알고리즘, 키
                .signWith(SignatureAlgorithm.HS512,SECURITY_KEY)
                //jwt 제목, 생성일, 만료일 설정
                .setSubject(userEmail).setIssuedAt(new Date()).setExpiration(exprTime)
                .compact();
    }
    //jwt 검증
    public String validate(String token){
        //token을 키를 사용해서 디코딩
        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
        //디코딩된 payload에서 제목을 가져옴
        return claims.getSubject();
    }
}
