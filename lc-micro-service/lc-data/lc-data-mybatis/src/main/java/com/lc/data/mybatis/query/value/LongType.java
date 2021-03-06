package com.lc.data.mybatis.query.value;

import cn.hutool.core.util.StrUtil;
import com.lc.data.mybatis.query.ValueType;
import com.lc.data.mybatis.query.ValueTypeEnum;

/**
 * <p> 长整型 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public class LongType implements ValueType<Long> {

    @Override
    public ValueTypeEnum getRangeDataType() {
        return ValueTypeEnum.LONG;
    }

    @Override
    public Long transformValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }

        return Long.valueOf(value);
    }

}
