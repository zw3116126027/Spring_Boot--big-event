package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ArticleMapper;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.service.ArticleService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleServicelmp
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/14 17:42
 * @Version 1.0
 **/
@Service
public class ArticleServicelmp implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId) ;
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBan对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);
        //3.调用mapper
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //返回当前页的查询数据列表
        List<Article> as= articleMapper.list(userId,categoryId,state);
        //page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal()); //设置总记录数
        pb.setItems(p.getResult());//设置当前页的数据列表
        return pb;
    }

    @Override
    public Article findById(Integer id) {
        Article a =articleMapper.findById(id);
        return a;
    }

    @Override
    public void upArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.upArticle(article);
    }

    @Override
    public void Delete(Integer id) {
        articleMapper.Delete(id);
    }

    @Override
    public List<Article> allList() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer create_userId = (Integer) map.get("id");
        return articleMapper.allList(create_userId);
    }
}
