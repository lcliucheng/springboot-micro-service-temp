package com.lc.data.mybatis.query.value;

import cn.hutool.core.util.StrUtil;
import com.lc.data.mybatis.query.ValueType;
import com.lc.data.mybatis.query.ValueTypeEnum;

/**
 * <p> 整型 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public class IntegerType implements ValueType<Integer> {

    @Override
    public ValueTypeEnum getRangeDataType() {
        return ValueTypeEnum.INTEGER;
    }

    @Override
    public Integer transformValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }

}
