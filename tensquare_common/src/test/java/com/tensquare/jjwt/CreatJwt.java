package com.tensquare.jjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JJWT java json web token：头部Header（声明类型，声明加密算法）
 *                            +载荷Claims（存放有效信息）
 *                             +签证Signature（自己生成）
 *  跨域 无状态 去耦 移动端 scrf（跨域伪造） 性能
 */
public class CreatJwt {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"xiaoming") //签名算法 密匙
                .setId("1")
                .setSubject("xiaoming")
                .setIssuedAt(new Date());

        System.out.println(jwtBuilder.compact());
    }
}
