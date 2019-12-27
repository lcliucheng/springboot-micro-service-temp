package com.lc.data.mybatis.query;

import com.google.common.collect.Maps;
import com.lc.data.mybatis.query.value.*;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

/**
 * <p> 范围数据类型工厂 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
@NoArgsConstructor
public final class ValueTypeFactory {

    private static Map<ValueTypeEnum, ValueType<? extends Comparable>> valueTypeMap = Maps.newHashMap();

    static {
        addValueType(new DateType());
        addValueType(new DateTimeType());
        addValueType(new IntegerType());
        addValueType(new LongType());
        addValueType(new StringType());
    }


    /**
     * 范围数据值
     *
     * @param valueTypeEnum ValueTypeEnum
     * @return ValueType
     */
    public static ValueType<? extends Comparable> getValueType(ValueTypeEnum valueTypeEnum) {
        return valueTypeMap.get(valueTypeEnum);
    }

    /**
     * 转换值
     *
     * @param valueTypeEnum ValueTypeEnum
     * @param value         字符串
     * @return V
     */
    public static Comparable transformValue(ValueTypeEnum valueTypeEnum, String value) {
        ValueType<? extends Comparable> valueType = getValueType(valueTypeEnum);
        if (Objects.isNull(valueType)) {
            return null;
        }

        return valueType.transformValue(value);
    }

    /**
     * 转换值
     *
     * @param valueType RangeDataType字符串
     * @param value     字符串
     * @return V
     */
    public static Comparable transformValue(String valueType, String value) {
        ValueTypeEnum valueTypeEnum = ValueTypeEnum.valueOf(valueType);
        Assert.notNull(valueTypeEnum, "参数数据类型格式错误");
        return transformValue(valueTypeEnum, value);
    }

    /**
     * 添加范围数据类型
     *
     * @param valueType ValueType
     */
    public static void addValueType(ValueType<? extends Comparable> valueType) {
        valueTypeMap.put(valueType.getRangeDataType(), valueType);
    }

}
