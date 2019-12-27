package com.lc.validator;

import com.lc.validator.constraints.Domain;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 域名验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
@SuppressWarnings("WeakerAccess")
public class DomainValidator implements ConstraintValidator<Domain, String> {

    private static final Pattern DOMAIN_PATTERN = Pattern.compile("(?!_)(?!-)(\\w|-){1,64}");

    /**
     * 校验域名
     *
     * @param domain 域名
     * @return 通过返回true，不通过返回false
     */
    public static boolean isDomain(String domain) {
        return DOMAIN_PATTERN.matcher(domain).matches();
    }

    @Override
    public void initialize(Domain constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || isDomain(value);
    }

}
