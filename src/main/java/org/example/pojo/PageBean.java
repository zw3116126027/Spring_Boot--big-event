package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//分页返回结果对象
@Data
@NoArgsConstructor  //生成一个无参构造函数
@AllArgsConstructor //成全参数构造函数
public class PageBean <T>{
    private Long total;//总条数
    private List<T> items;//当前页数据集合
}
