package com.lc.data.mybatis.query.json;

import com.google.common.collect.Lists;
import com.lc.data.mybatis.query.QueryWrapperHelper;
import com.lc.data.mybatis.query.ValueTypeEnum;
import com.lc.data.mybatis.query.ValueTypeFactory;
import com.lc.enums.common.YesOrNo;
import com.lc.validator.SafeValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

/**
 * Json查询
 *
 * @author liucheng
 * @since 2019-12-02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonHelper {

    private static final String SPLIT = ",";
    private static final String ALIAS_SPLIT = ".";
    private static final int MIN_LENGTH = 4;

    /**
     * 获取Json对象
     *
     * @param inStr 集合字符串值
     * @return 集合对象
     */
    public static JsonValue<Comparable> getJsonValue(String inStr) {
        String[] values = inStr.split(SPLIT);
        int length = values.length;

        Assert.isTrue(length >= MIN_LENGTH, "Json参数格式错误");

        String field = values[0];
        String fieldStr = field;
        if(fieldStr.contains(ALIAS_SPLIT)) {
            fieldStr = fieldStr.substring(fieldStr.indexOf(".")+1);
        }
        Assert.isTrue(QueryWrapperHelper.hasField(fieldStr), "Json参数字段格式错误");

        String dataType = values[2];

        //判断是否包含isArray参数
        int startIndex = 3;
        boolean isArray = false;
        String isArrayStr = values[startIndex];
        if (Objects.equals(YesOrNo.YES.getValue().toString(), isArrayStr)) {
            Assert.isTrue(length >= MIN_LENGTH + 1, "Json参数格式错误");
            isArray = true;
            startIndex = startIndex + 1;
        } else if (Objects.equals(YesOrNo.NO.getValue().toString(), isArrayStr)) {
            Assert.isTrue(length >= MIN_LENGTH + 1, "Json参数格式错误");
            startIndex = startIndex + 1;
        }

        ValueTypeEnum valueTypeEnum = ValueTypeEnum.valueOf(dataType);
        List<Comparable> val = Lists.newArrayList();
        for (int i = startIndex; i < length; i++) {
            val.add(ValueTypeFactory.transformValue(valueTypeEnum, SafeValidator.getSafeStr(values[i])));
        }

        return new JsonValue<>(field, values[1], isArray, val, valueTypeEnum);
    }

}
