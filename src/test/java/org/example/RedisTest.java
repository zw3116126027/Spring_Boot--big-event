package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisTest
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/19 13:43
 * @Version 1.0
 **/
@SpringBootTest
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet(){
     //往redis中存一个键值对 stringRedisTemplate
        stringRedisTemplate.opsForValue().set("name", "张三");
        stringRedisTemplate.opsForValue().set("id","2",15, TimeUnit.SECONDS);
    }
    @Test
    public void testGet(){
        //往redis中存一个键值对 stringRedisTemplate
        System.out.println(stringRedisTemplate.opsForValue().get("name"));

    }
}
