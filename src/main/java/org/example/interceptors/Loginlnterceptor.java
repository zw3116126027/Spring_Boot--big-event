package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.JwtUtil;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @ClassName Loginlnterceptor
 * @Description 拦截器
 * @Author kli
 * @DATE 2024/9/11 20:35
 * @Version 1.0
 **/
@Component
public class Loginlnterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        //允许开发者从 HTTP 请求中获取特定的请求头
        String token = request.getHeader("Authorization");
        //验证token
        try {
            //从redis获取相同的token
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //把浏览器的Authorization(请求头)对应的token值 作为健去Redis 中找对应值
            // 没有找到对应的令牌(之前存的键值都为token)，则抛出异常，表示令牌已失效
            String redisToken = operations.get(token);
            if (redisToken==null){
                //token已经失效了
                throw new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到Threadlocal中
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(401);
            //不放行
            return false;
        }
    }
        //后置拦截器
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据 防止内存泄露
        ThreadLocalUtil.remove();
    }
}
