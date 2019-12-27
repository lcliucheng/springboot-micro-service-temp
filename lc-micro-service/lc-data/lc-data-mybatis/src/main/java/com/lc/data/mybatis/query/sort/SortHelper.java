package com.lc.data.mybatis.query.sort;

import com.lc.validator.SafeValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

/**
 * 排序
 *
 * @author liucheng
 * @since 2019-12-29
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SortHelper {

    private static final String SPLIT = ",";
    private static final String[] DIRECTIONS = new String[]{"ASC", "DESC"};
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 2;

    /**
     * 获取排序对象
     *
     * @param sortStr 排序字符串值
     * @return 范围对象
     */
    public static SortValue getSort(String sortStr) {
        String[] values = sortStr.split(SPLIT);

        Assert.isTrue(values.length >= MIN_LENGTH && values.length <= MAX_LENGTH,
                "Sort参数格式错误");
        String field = SafeValidator.getSafeStr(values[0]);

        String direction = "ASC";
        if (values.length == MAX_LENGTH) {
            for (String s : DIRECTIONS) {
                if (s.equalsIgnoreCase(values[1])) {
                    direction = SafeValidator.getSafeStr(values[1]).toUpperCase();
                    break;
                }
            }

            return new SortValue(field, direction);
        }

        return new SortValue(field, direction);
    }

}
