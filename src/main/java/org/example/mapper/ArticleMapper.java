package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增
    @Insert("insert into article (title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            " values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    //条件分页查询 用动态sql xml 文件写sql
    List<Article> list(Integer userId, Integer categoryId, String state);

    //根据id查询
    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    //更新
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg}" +
            ",state=#{state},category_id=#{categoryId} ,update_time=#{updateTime} where id=#{id}")
    void upArticle(Article article);

    //删除
    @Delete("DELETE  from article where id=#{id}")
    void Delete(Integer id);

    //按照create_userId查询全部
    @Select("select * from article where create_user=#{create_userId}")
    List<Article> allList(Integer create_userId);
}
