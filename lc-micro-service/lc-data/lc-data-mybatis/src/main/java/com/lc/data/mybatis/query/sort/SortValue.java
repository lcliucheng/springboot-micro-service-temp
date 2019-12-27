package com.lc.data.mybatis.query.sort;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 排序
 *
 * @author liucheng
 * @since 2019-12-02
 */
@Data
@AllArgsConstructor
public class SortValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字段
     */
    private String filed;

    /**
     * 排序方式 ASC、DESC
     */
    private String direction;

}
