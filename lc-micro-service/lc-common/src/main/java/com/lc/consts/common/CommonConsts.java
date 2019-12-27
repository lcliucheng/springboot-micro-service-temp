package com.lc.consts.common;


import java.time.format.DateTimeFormatter;

/**
 * 常量
 *
 * @author liucheng
 * @since 2019-12-28
 */
public interface CommonConsts {

    /**
     * 日期格式化
     */
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 日期格式化
     */
    DateTimeFormatter DATE_EX_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * 日期格式化
     */
    DateTimeFormatter DATE_MINI_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 日期格式化
     */
    DateTimeFormatter CHINESE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    /**
     * 日期格式化
     */
    DateTimeFormatter DATE_POINT_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    /**
     * 时间格式化
     */
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间格式化
     */
    DateTimeFormatter DATE_TIME_MINI_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 时间格式化
     */
    DateTimeFormatter DATE_TIME_MILLI_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

    /**
     * 时间格式化
     */
    DateTimeFormatter DATE_TIME_EX_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
}
