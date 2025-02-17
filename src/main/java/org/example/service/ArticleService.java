package org.example.service;

import org.example.pojo.Article;
import org.example.pojo.PageBean;

import java.util.List;

public interface ArticleService  {
    //新增文章
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //根据ID获取文章详细信息
    Article findById(Integer id);

    //更新文章信息
    void upArticle(Article article);

    //根据ID删除文章
    void Delete(Integer id);

    //查询所有文章列表
    List<Article> allList();
}
