package com.lc.data.mybatis.query.in;

import com.google.common.collect.Lists;
import com.lc.data.mybatis.query.QueryWrapperHelper;
import com.lc.data.mybatis.query.ValueTypeFactory;
import com.lc.enums.common.YesOrNo;
import com.lc.validator.SafeValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 集合查询
 *
 * @author liucheng
 * @since 2019-12-02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InHelper {

    private static final String SPLIT = ",";
    private static final String ALIAS_SPLIT = ".";
    private static final int MIN_LENGTH = 3;

    /**
     * 获取集合对象
     *
     * @param inStr 集合字符串值
     * @return 集合对象
     */
    public static InValue<Comparable> getInValue(String inStr) {
        String[] values = inStr.split(SPLIT);
        int length = values.length;

        Assert.isTrue(length >= MIN_LENGTH, "In参数格式错误");

        //判断是否包含isNegated参数
        int startIndex = 0;
        boolean isNegated = false;
        String isNegatedStr = values[startIndex];
        if (Objects.equals(YesOrNo.YES.getValue().toString(), isNegatedStr)) {
            Assert.isTrue(length >= MIN_LENGTH + 1, "In参数格式错误");
            isNegated = true;
            startIndex = startIndex + 1;
        } else if (Objects.equals(YesOrNo.NO.getValue().toString(), isNegatedStr)) {
            Assert.isTrue(length >= MIN_LENGTH + 1, "In参数格式错误");
            startIndex = startIndex + 1;
        }

        String field = values[startIndex];
        String fieldStr = field;
        if(fieldStr.contains(ALIAS_SPLIT)) {
            fieldStr = fieldStr.substring(fieldStr.indexOf(".")+1);
        }
        Assert.isTrue(QueryWrapperHelper.hasField(fieldStr), "In参数字段格式错误");

        String dataType = SafeValidator.getSafeStr(values[startIndex + 1]);
        int valueIndex = startIndex + 2;
        List<Comparable> val = Lists.newArrayList();
        for (int i = valueIndex; i < length; i++) {
            if (!StringUtils.isEmpty(values[i])) {
                val.add(ValueTypeFactory.transformValue(dataType, SafeValidator.getSafeStr(values[i])));
            }
        }

        return new InValue<>(isNegated, field, val);
    }

}
