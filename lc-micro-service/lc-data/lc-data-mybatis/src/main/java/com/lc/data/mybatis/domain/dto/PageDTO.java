package com.lc.data.mybatis.domain.dto;

import com.lc.annotation.Encrypt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 分页传输对象
 *
 * @author liucheng
 * @since 2019-12-26
 */
@ApiModel(value = "PageDTO", description = "分页传输对象")
@Data
@Accessors(chain = true)
public class PageDTO<T> implements Serializable {

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    @Encrypt
    private List<T> records;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Long total;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Long current;

    /**
     * 每页记录数，默认 10
     */
    @ApiModelProperty(value = "每页记录数")
    private Long size;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Long pages;

    @ApiModelProperty(value = "是否隐藏 0-否 1-是")
    private Integer hidden;

    @ApiModelProperty(value = "自定义扩展对象")
    private Object object;

    public List<T> getRecords() {
        if (Objects.isNull(records)) {
            return Collections.emptyList();
        }
        return records;
    }

    @ApiModelProperty(value = "是否首页")
    public boolean isFirst() {
        return (1 == current);
    }

    @ApiModelProperty(value = "是否尾页")
    public boolean isLast() {
        return pages.compareTo(current) <= 0;
    }

}
