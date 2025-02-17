package org.example;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class JwtTest{

    @Test
    public void testGen(){
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生成jwt的代码
        String token= JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12)) //添加过期时间
                .sign(Algorithm.HMAC256("itheima")); //指定算法，配置密钥
        System.out.println(token);
    }
    @Test
    public void testParse(){

        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MjYwOTQ3ODJ9" +
                ".Z3ek8GCqFebumHd459K2KDAxTHdQA6rte_tyOR4kfRQ";
        JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256("itheima")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token); //验证token,生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));


        //如果纂改了头部和载荷部分的数据,那么验证失败
        //如果密钥改了，验证失败
        //token过期
        //Header(头),记录令牌类型和签名算法等
        //PayLoad(荷载),携带自定义的信息
        //Signature(签名),对头部和荷载进行加密计算得来
    }
}
