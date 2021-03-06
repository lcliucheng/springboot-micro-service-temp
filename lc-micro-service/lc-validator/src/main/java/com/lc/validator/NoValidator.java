package com.lc.validator;

import com.lc.validator.constraints.No;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;


/**
 * 编号验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SuppressWarnings("WeakerAccess")
public class NoValidator implements ConstraintValidator<No, String> {

    private static final Pattern NO_PATTERN = Pattern.compile("(?!_)(?!-)(\\w|-){1,64}");

    /**
     * 校验编号
     *
     * @param code 编号
     * @return 通过返回true，不通过返回false
     */
    public static boolean isNo(String code) {
        return NO_PATTERN.matcher(code).matches();
    }

    @Override
    public void initialize(No constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || isNo(value);
    }

}
