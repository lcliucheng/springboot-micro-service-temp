package com.lc.data.mybatis.query.json;

import com.lc.data.mybatis.query.ValueTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Json数据
 *
 * @author liucheng
 * @since 2019-12-02
 */
@Data
@AllArgsConstructor
public class JsonValue<V extends Comparable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    private String field;

    /**
     * Json路径
     */
    private String path;

    /**
     * 是否数组
     */
    private boolean isArray;

    /**
     * 值
     */
    private List<V> val;

    /**
     * 值类型
     */
    private ValueTypeEnum valueTypeEnum;

}
