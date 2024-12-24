package com.lv.validated;

import com.lv.anno.ArticleState;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @projectName: wangzai
 * @package: com.lv.anno
 * @className: ArticleStateValidated
 * @author: dus
 * @description:
 * @date: 2024/12/24 10:29
 * @version: 1.0
 */
public class ArticleStateValidated implements ConstraintValidator<ArticleState, String> {


    @Override
    public void initialize(ArticleState constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String state, ConstraintValidatorContext constraintValidatorContext) {
        if (state == null){
            return false;
        }
        if ("已发布".equals(state) || "草稿".equals(state)){
            return true;
        }
        return false;
    }

}
