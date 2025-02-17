package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty()
    private String categoryName;//分类名称
    @NotEmpty()
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "UTC")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "UTC")
    private LocalDateTime updateTime;//更新时间

    //如果说某个校验码没有指定分组,默认属于Default分组
    //分组直接可以继承,A extends B 那么A中拥有B中所有的校验码
    public interface Add extends Default {

    }
    public interface Update extends Default{

    }
}
