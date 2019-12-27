package com.lc.validator;

import com.lc.exception.BaseRuntimeException;
import com.lc.validator.constraints.Safe;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;
import org.owasp.validator.html.*;
/**
 * 安全参数验证器
 *
 * @author liucheng
 * @since 2019-12-15
 */
public class SafeValidator implements ConstraintValidator<Safe, String> {

    private static Policy policy;

    static {
        Resource resource = new ClassPathResource ("antisamy.xml");
        try (InputStream is = resource.getInputStream()) {
            policy = Policy.getInstance(is);
        } catch (IOException e) {
            throw new BaseRuntimeException ("antisamy xml error", e);
        } catch (PolicyException e) {
            throw new BaseRuntimeException("antisamy xml policy error", e);
        }
    }

    /**
     * 校验是否包含不安全的脚本
     *
     * @param value 值
     * @return boolean
     */
    public static boolean isSafe(String value) {
        AntiSamy antiSamy = new AntiSamy();
        try {
            final CleanResults cleanResults = antiSamy.scan(value, policy);
            //判断是否有不合法的字符
            return cleanResults.getNumberOfErrors() <= 0;
        } catch (ScanException | PolicyException e) {
            return false;
        }
    }

    /**
     * 获取安全的字符串
     *
     * @param value 值
     * @return 字符串
     */
    public static String getSafeStr(String value) {
        AntiSamy antiSamy = new AntiSamy();
        try {
            final CleanResults cleanResults = antiSamy.scan(value, policy);
            //安全的HTML输出
            return cleanResults.getCleanHTML();
        } catch (ScanException | PolicyException e) {
            return "";
        }
    }

    @Override
    public void initialize(Safe constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.isBlank(value) || isSafe(value);
    }

}
