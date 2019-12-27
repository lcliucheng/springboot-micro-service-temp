package com.lc.data.mybatis.query;

/**
 * <p> 值类型 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public interface ValueType<V extends Comparable> {

    /**
     * 获取数据类型
     *
     * @return ValueTypeEnum
     */
    ValueTypeEnum getRangeDataType();

    /**
     * 转换
     *
     * @param value 字符串
     * @return V
     */
    V transformValue(String value);

}
