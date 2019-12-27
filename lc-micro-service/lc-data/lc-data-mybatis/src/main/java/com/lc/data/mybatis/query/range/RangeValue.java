package com.lc.data.mybatis.query.range;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 范围
 *
 * @author liucheng
 * @since 2019-12-02
 */
@Data
@AllArgsConstructor
public class RangeValue<V extends Comparable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    private String field;

    /**
     * 小值
     */
    private V lowerBound;

    /**
     * 大值
     */
    private V upperBound;

}
