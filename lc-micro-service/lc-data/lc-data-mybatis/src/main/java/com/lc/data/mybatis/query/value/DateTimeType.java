package com.lc.data.mybatis.query.value;

import cn.hutool.core.util.StrUtil;
import com.lc.consts.common.CommonConsts;
import com.lc.data.mybatis.query.ValueType;
import com.lc.data.mybatis.query.ValueTypeEnum;

import java.time.LocalDateTime;

/**
 * <p> 时间 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public class DateTimeType implements ValueType<LocalDateTime> {

    @Override
    public ValueTypeEnum getRangeDataType() {
        return ValueTypeEnum.DATE_TIME;
    }

    @Override
    public LocalDateTime transformValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }

        return LocalDateTime.parse(value, CommonConsts.DATE_TIME_FORMATTER);
    }

}
