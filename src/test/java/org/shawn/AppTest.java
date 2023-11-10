package org.shawn;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {

    @Test
    public void testJWTGen(){
        //使用map，是因为用户信息有多个，map存储比较方便
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");

        //生成jwt
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))//添加过期时间
                .sign(Algorithm.HMAC256("NYUHEIMA"));//自定义的密钥

        System.out.println(token);
    }

    @Test
    public void parseJWT(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE2OTk2OTg5Nzd9.U_jnc0KBER1nCh14lkDlaw1HzFUdqHyr4YzuER5ufdM";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("NYUHEIMA")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }

}
