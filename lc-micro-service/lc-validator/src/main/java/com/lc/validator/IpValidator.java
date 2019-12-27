package com.lc.validator;

import cn.hutool.core.lang.Validator;
import com.lc.validator.constraints.Ip;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Ip验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
public class IpValidator implements ConstraintValidator<Ip, String> {

    @Override
    public void initialize(Ip constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || Validator.isIpv4(value);
    }

}
