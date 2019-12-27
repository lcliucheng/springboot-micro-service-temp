package com.lc.validator;


import cn.hutool.core.util.IdcardUtil;
import com.lc.validator.constraints.IdCard;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 身份证号码验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    @Override
    public void initialize(IdCard constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || IdcardUtil.isValidCard(value);
    }

}
