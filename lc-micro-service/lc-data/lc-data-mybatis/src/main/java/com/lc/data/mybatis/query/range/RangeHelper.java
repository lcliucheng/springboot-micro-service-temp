package com.lc.data.mybatis.query.range;

import com.lc.data.mybatis.query.QueryWrapperHelper;
import com.lc.data.mybatis.query.ValueTypeFactory;
import com.lc.validator.SafeValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

/**
 * 范围擦汗寻
 *
 * @author liucheng
 * @since 2019-12-02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RangeHelper {

    private static final String SPLIT = ",";
    private static final String ALIAS_SPLIT = ".";
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 4;

    /**
     * 获取范围对象
     *
     * @param rangeStr 范围字符串值
     * @return 范围对象
     */
    public static RangeValue<Comparable> getRangeValue(String rangeStr) {
        String[] values = rangeStr.split(SPLIT);

        Assert.isTrue(values.length >= MIN_LENGTH && values.length <= MAX_LENGTH,
                "Range参数格式错误");

        String field = values[0];
        String fieldStr = field;
        if(fieldStr.contains(ALIAS_SPLIT)) {
            fieldStr = fieldStr.substring(fieldStr.indexOf(".")+1);
        }
        Assert.isTrue(QueryWrapperHelper.hasField(fieldStr), "Range参数字段格式错误");

        Comparable form = ValueTypeFactory.transformValue(values[1], SafeValidator.getSafeStr(values[2]));

        Comparable to = null;
        if (values.length == MAX_LENGTH) {
            to = ValueTypeFactory.transformValue(values[1], SafeValidator.getSafeStr(values[3]));
        }

        return new RangeValue<>(field, form, to);
    }

}
