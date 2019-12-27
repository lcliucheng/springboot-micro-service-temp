package com.lc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  字段别名
 * @author liucheng
 * @since 2019/10/8 15:05
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FiledAliasName {

    /**字段别名**/
    String value() default  "";

    String condition() default "";

}
