package com.lc.data.mybatis.query.in;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 集合
 *
 * @author liucheng
 * @since 2019-12-02
 */
@Data
@AllArgsConstructor
public class InValue<V extends Comparable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否否定
     */
    private boolean isNegated;

    /**
     * 字段
     */
    private String field;

    /**
     * 值
     */
    private List<V> val;

}
