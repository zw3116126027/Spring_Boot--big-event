package org.example.controller;

import jakarta.validation.constraints.Pattern;
import org.example.pojo.Category;
import org.example.pojo.Result;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/12 17:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs =categoryService.list();
        return Result.success(cs);
    }
    @GetMapping("/detail")
    public Result<Category> datail(@RequestParam @Pattern(regexp = "^[1-9]\\d*$",message = "id格式错误") String id){

        Category cs =categoryService.findById(Integer.valueOf(id));
        if(cs!=null){
            return Result.success(cs);
        }
        return Result.error("不存在该id");
    }
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
    @DeleteMapping
    public Result Delete(@RequestParam Integer id){
        categoryService.DeletByid(id);
        return Result.success();
    }
}
