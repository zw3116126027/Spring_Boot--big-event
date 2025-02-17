package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.example.pojo.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.HOURS;


/**
 * @ClassName UserController
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/10 14:53
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
@Validated
//支持分组验证，可以更细致地控制验证过程 用在类、方法和方法参数上，但不能用于成员属性
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        // ^ 表示字符串开始。\\S 表示非空白字符（即字母、数字、符号等）$ 表示字符串结束。
        //查询用户
        User u = userService.findByUserName(username);
        if(u==null){
            //没有占用
            //注册
            userService.register(username,password);
            return Result.success();
        }else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        User loginUser=userService.findByUserName(username);
            //判断用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
            //判断密码是否正确 也就是Md5Util在数据库加密的密文
        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //登录成功
            //接收业务数据,生成token并返回
            Map<String,Object> claims=new HashMap<>();
            //添加载荷
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token 存储到redis中 过期时间1小时
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            //operations.set(键,键值,过期时间,时间单位)
            operations.set(token,token,1, HOURS);
            return Result.LoginSuccess(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        //根据用户名查询用户
        /*Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        //更新用户基本信息
        //id校验
        final Map<String,Object> map = ThreadLocalUtil.get();
        final Integer id= (Integer) map.get("id");

        if (user.getId().equals(id)){
            userService.update(user);
            return Result.success();
        }else {
            return Result.error("非本人id");
        }

    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map <String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String old_pwd = params.get("old_pwd");
        String new_pwd = params.get("new_pwd");
        String re_pwd = params.get("re_pwd");
        //判断是否输入了三个参数
        if(!StringUtils.hasLength(old_pwd)||!StringUtils.hasLength(new_pwd)||!StringUtils.hasLength(re_pwd)){
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        //调用userService根据用户名拿到原密码,再和old_pwd对比
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        //将数据库的加密格式密码 和 输入的老密码加密 进行对比
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(old_pwd))){
            return Result.error("原密码填写不正确");
        }

        //判断newPaw和rePwd 是否一样
        if(!re_pwd.equals(new_pwd)){
            return Result.error("两次填写的新密码不一样");
        }
        //2.调用service
        userService.updatePwd(new_pwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
}
