package org.example.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.service.ArticleService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/11 17:56
 * @Version 1.0
 **/
@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    @PostMapping
    public  Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }
    //required = false 请求中没有提供这个参数，Spring将不会抛出异常 值设置为 null
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb= articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }
    @GetMapping("/detail")
    public Result<Article> datail(@RequestParam @Pattern(regexp = "^[1-9]\\d*$",message = "id格式错误") String id){
        Article data= articleService.findById(Integer.valueOf(id));
        if (data!=null){
            return Result.success(data);
        }return Result.error("不存在该id");
    }
    @PutMapping
    public Result upArticle(@RequestBody @Validated Article article){
        articleService.upArticle(article);
        return Result.success();
    }
    @DeleteMapping
    public Result Delete(Integer id){
        articleService.Delete(id);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<Article>> allList(){
        List<Article> data=articleService.allList();
        return Result.success(data);
    }
}
