package com.lc.validator;

import com.lc.validator.constraints.Password;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 密码验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SuppressWarnings("WeakerAccess")
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![^A-Za-z0-9]+$)[\\S]{8,25}$");

    /**
     * 校验密码
     *
     * @param password 密码
     * @return 通过返回true，不通过返回false
     */
    public static boolean isPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || isPassword(value);
    }

}
