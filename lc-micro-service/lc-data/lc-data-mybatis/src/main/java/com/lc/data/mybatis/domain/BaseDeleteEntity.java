package com.lc.data.mybatis.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 基础字段
 *
 * @author liucheng
 * @since 2019-05-02
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseDeleteEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "删除人", hidden = true)
    @TableField(value = "deleted_by", fill = FieldFill.INSERT)
    private String deletedBy;

    @ApiModelProperty(value = "删除时间", hidden = true)
    @TableField(value = "deleted_at", fill = FieldFill.INSERT)
    private LocalDateTime deletedAt;

}
