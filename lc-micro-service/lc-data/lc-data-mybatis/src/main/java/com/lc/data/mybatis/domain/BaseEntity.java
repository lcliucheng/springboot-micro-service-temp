package com.lc.data.mybatis.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 包含乐观锁的基础字段
 *
 * @author liucheng
 * @since 2019-05-02
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntity extends BaseMiniEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @TableField(value = "last_modified_at",el = "lastModifiedAt, typeHandler = com.zzjr.data.mybatis.handler.LocalDateTimeTypeHandler", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedAt;

    @ApiModelProperty(value = "版本号", hidden = true)
    @Version
    @TableField(value = "version", fill = FieldFill.INSERT)
    private Long version;

}
