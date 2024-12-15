package com.lv;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: wangzai
 * @ClassName JwtTest
 * @description:
 * @author: gxjh
 * @create: 2024-12-14 18:32
 * @Version 1.0
 **/

public class JwtTest {


    /**
     * 测试生成jwt token
     */
    @Test
    public void testGenericJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id", "1");
        claims.put("username", "张三");


        //生成jwt的代码
        String token = JWT.create()
                .withClaim("username",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//添加过期时间
                .sign(Algorithm.HMAC256("itlv"));//指定算法，设置密钥

        System.out.println(token);
    }

    /**
     * 测试解析验证token
     * 如果篡改头部或载荷  失败
     * 如果篡改算法，密钥  失败
     * 如果token过期     失败
     */
    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6eyJ1c2VySWQiOiLlvKDkuIkifSwiZXhwIjoxNzM0MjE1OTg0fQ.685FflrmbVDGY3lQiaLmoNeAsWs4cQuwZ14ykXvhVCY";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itlv")).build();
        DecodedJWT verify = jwtVerifier.verify(token);//解析后的token

        Map<String, Claim> claims = verify.getClaims();
        System.out.println(claims.get("username"));


    }
}