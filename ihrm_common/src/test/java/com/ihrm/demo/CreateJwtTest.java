package com.ihrm.demo;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.swing.*;
import java.util.Date;

public class CreateJwtTest {

    /**
     * 通过jjwt创建token
     */
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder().setId("88").setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "ihrm")
                .claim("companyId","123456")
                .claim("companyName","江苏传智播客教育股份有限公司")
                ;
        String token = jwtBuilder.compact();
        System.out.println(token);
    }
}
