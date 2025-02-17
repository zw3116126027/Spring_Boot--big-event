package org.example.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@NoArgsConstructor  //自动生成无参构造函数
@AllArgsConstructor
@Data
public //自动生成全参数构造函数
class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }
    public static <E> Result<E> LoginSuccess(E data) {
        return new Result<>(0, "登录成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }
    public static Result error(String  data) {
        return new Result(1, "操作失败", null);
    }
}
