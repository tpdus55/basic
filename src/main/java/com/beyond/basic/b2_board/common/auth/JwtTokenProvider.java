package com.beyond.basic.b2_board.common.auth;

import com.beyond.basic.b2_board.author.domain.Author;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider { //기능만 수행하는 객체

//    중요정보의 경우 application.yml 저장. Value를 통해 주입.
    @Value("${jwt.secretKey}")
    private String st_secret_key; //디코딩+암호화

    @Value("${jwt.expiration}")
    private int expiration;

//    인코딩 된 문자열 -> 디코딩 된 문자열 -> HS512알고리즘으로 암호화
    private Key secret_key;

//    생성자 호출 이후에 아래 메서드를 실행하게 함으로써, @Value시점보다 늦게 실행되게 하여 값주입의 문제해결.
    @PostConstruct
    public void init(){
        secret_key = new SecretKeySpec(Base64.getDecoder().decode(st_secret_key), SignatureAlgorithm.HS512.getJcaName());
    }
    public String createToken(Author author){

        //sub : abc@naver.com 형태
        Claims claims = Jwts.claims().setSubject(author.getEmail());
//        주된 키값을 제외한 나머지 정보는 put을 사용하여 key:value로 세팅
        claims.put("role",author.getRole().toString());
//        ex) claims.put("age",author.getAge()); 형태가능

        Date now = new Date();
//
//        토큰의 구성요소 : 헤더, 페이로드, 시그니처(서명부)
        String token = Jwts.builder()
//                아래 3가지 요소는 페이로드
                .setClaims(claims)
                .setIssuedAt(now) //발행시간
                .setExpiration(new Date(now.getTime() + expiration*60*1000L)) //만료시간 , 30분*60초*1000밀리초 : 밀리초형태로 변환한것
//                secret키를 통해 서명값(signature) 생성
                .signWith(secret_key)
                .compact();
        return token;
    }
}
