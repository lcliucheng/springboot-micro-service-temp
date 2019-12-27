package com.lc.validator;


import cn.hutool.core.lang.Validator;
import com.lc.validator.constraints.Mobile;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    @Override
    public void initialize(Mobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || Validator.isMobile(value);
    }

}
