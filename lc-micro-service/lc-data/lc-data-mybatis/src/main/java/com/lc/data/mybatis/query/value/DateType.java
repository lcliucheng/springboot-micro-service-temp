package com.lc.data.mybatis.query.value;

import cn.hutool.core.util.StrUtil;
import com.lc.consts.common.CommonConsts;
import com.lc.data.mybatis.query.ValueType;
import com.lc.data.mybatis.query.ValueTypeEnum;

import java.time.LocalDate;

/**
 * <p> 日期 </p>
 *
 * @author liucheng
 * @since 2019-12-02
 */
public class DateType implements ValueType<LocalDate> {

    @Override
    public ValueTypeEnum getRangeDataType() {
        return ValueTypeEnum.DATE;
    }

    @Override
    public LocalDate transformValue(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }

        return LocalDate.parse(value, CommonConsts.DATE_FORMATTER);
    }

}
