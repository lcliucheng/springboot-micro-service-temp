package com.lc.data.mybatis.query.value;

import cn.hutool.core.util.StrUtil;
import com.lc.data.mybatis.query.ValueType;
import com.lc.data.mybatis.query.ValueTypeEnum;

/**
 * <p> 字符串类型 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public class StringType implements ValueType<String> {

    @Override
    public ValueTypeEnum getRangeDataType() {
        return ValueTypeEnum.STRING;
    }

    @Override
    public String transformValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }

        return value;
    }

}
