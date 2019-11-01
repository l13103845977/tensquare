package com.tensquare.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

/**
 * 解密jwt
 */
public class ParseJwt {

    @Test
    public  void  parseJwt(){
        Claims claims =
                Jwts.parser().setSigningKey("xiaoming")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoieGlhb21pbmciLCJpYXQiOjE1NTEzMTkwMTJ9.IrK7b2HduLwJCJOjZquSaNYQCodjFNDiVeLq0PT8ges")
                .getBody();
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
    }
}
