package com.lc.uitls;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.lc.consts.common.CommonConsts;
import com.lc.exception.BaseRuntimeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p> 线程安全的Json工具类 </p>
 *
 * @author liucheng
 * @since 2019-04-15
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtil {

    /**
     * ObjectMapper 该类线程安全，可以全局公用一个
     */
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper ();
        objectMapper.setLocale (Locale.CHINA);

        //序列化忽略为空的字段
        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_NULL);

        //序列化处理
        objectMapper.configure (JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure (JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure (JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        //去掉默认的时间戳格式
        objectMapper.configure (SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //失败处理
        objectMapper.configure (SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //单引号处理
        objectMapper.configure (JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        //序列化时，日期的统一格式
        objectMapper.setDateFormat (new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", Locale.CHINA));

        //设置为中国上海时区
        objectMapper.setTimeZone (TimeZone.getTimeZone ("GMT-8:00"));

        //时间格式化
        JavaTimeModule javaTimeModule = new JavaTimeModule ();
        javaTimeModule.addSerializer (LocalDateTime.class
                , new LocalDateTimeSerializer (CommonConsts.DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer (LocalDate.class
                , new LocalDateSerializer (DateTimeFormatter.ofPattern ("yyyy-MM-dd")));
        javaTimeModule.addSerializer (LocalTime.class
                , new LocalTimeSerializer (DateTimeFormatter.ofPattern ("HH:mm:ss")));

        javaTimeModule.addDeserializer (LocalDateTime.class
                , new LocalDateTimeDeserializer (CommonConsts.DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer (LocalDate.class
                , new LocalDateDeserializer (DateTimeFormatter.ofPattern ("yyyy-MM-dd")));
        javaTimeModule.addDeserializer (LocalTime.class
                , new LocalTimeDeserializer (DateTimeFormatter.ofPattern ("HH:mm:ss")));
        objectMapper.registerModule (javaTimeModule);

        //长整型格式化
        SimpleModule simpleModule = new SimpleModule ();
        simpleModule.addSerializer (Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer (Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule (simpleModule);
    }

    /**
     * 把Bean转成字符串
     *
     * @param bean 对象
     * @return 字符串
     */
    public static <T> String toStr(T bean) {
        try {
            return objectMapper.writeValueAsString (bean);
        } catch (JsonProcessingException e) {
            throw new BaseRuntimeException (String.format ("Json Processing Exception,Bean[%s]", bean.toString ()), e);
        }
    }

    /**
     * 把Json字符串转成Bean
     *
     * @param json 字符串
     * @param cls  要转换的Bean的Class
     * @return Java对象
     */
    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return objectMapper.readValue (json, cls);
        } catch (JsonParseException e) {
            throw new BaseRuntimeException (String.format ("Json parse exception,String[%s]", json), e);
        } catch (JsonMappingException e) {
            throw new BaseRuntimeException (String.format ("Json mapping exception,String[%s],Class[%s]", json, cls), e);
        } catch (IOException e) {
            throw new BaseRuntimeException (String.format ("IO exception,String[%s],Class[%s]", json, cls), e);
        }
    }

    /**
     * 把Json字符串转成Bean
     *
     * @param json         字符串
     * @param valueTypeRef 要转换的Bean的valueTypeRef
     * @return Java对象
     */
    public static <T> T toBean(String json, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue (json, valueTypeRef);

        } catch (JsonParseException e) {
            throw new BaseRuntimeException (String.format ("Json parse exception,String[%s]", json), e);
        } catch (JsonMappingException e) {
            throw new BaseRuntimeException (String.format ("Json mapping exception,String[%s],Class[%s]", json, valueTypeRef), e);
        } catch (IOException e) {
            throw new BaseRuntimeException (String.format ("IO exception,String[%s],Class[%s]", json, valueTypeRef), e);
        }
    }

    /**
     * 转为为下划线json
     *
     * @param bean 转换对象
     * @return java.lang.String
     * @author liucheng
     * @date 2019/8/9
     */
    public static <T> String toBeanUnder(T bean) {

        ObjectMapper mapper = new ObjectMapper ();
        mapper.setPropertyNamingStrategy (com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);
        try {
            return mapper.writeValueAsString (bean);
        } catch (JsonProcessingException e) {
            throw new BaseRuntimeException (String.format ("Json Processing Exception,Bean[%s]", bean.toString ()), e);
        }
    }

    /**
     * 获取 ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}