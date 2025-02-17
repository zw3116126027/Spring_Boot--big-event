package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.anno.State;

/**
 * @ClassName StateValidation
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/15 20:46
 * @Version 1.0
 **/
                                        //给哪个注解提供校验规则,校验的数据类型
public class StateValidation implements ConstraintValidator<State,String> {
/**
 * @param value: 将来要校验的数据
 * @param context:
 * @return boolean 如果返回false,如果返回true,则校验通过
 * @author kli
 * @description TODO
 * @date 2024/9/15 21:08
 */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null){
            return false;
        }
        if(value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    }
}
