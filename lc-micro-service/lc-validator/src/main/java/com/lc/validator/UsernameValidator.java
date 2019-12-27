package com.lc.validator;

import com.lc.validator.constraints.Username;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 用户名验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SuppressWarnings("WeakerAccess")
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("(?!_)(?!-)(?![0-9]{5,32})(\\w|-){5,32}");

    /**
     * 校验用户名
     *
     * @param username 用户名
     * @return 通过返回true，不通过返回false
     */
    public static boolean isUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    @Override
    public void initialize(Username constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || isUsername(value);
    }

}
