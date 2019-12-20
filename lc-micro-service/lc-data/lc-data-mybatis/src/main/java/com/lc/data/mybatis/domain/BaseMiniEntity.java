package com.lc.data.mybatis.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 基础字段
 *
 * @author liucheng
 * @since 2019-05-02
 */
@Data
public abstract class BaseMiniEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "扩展字段", hidden = true)
    @TableField(value = "ext", el = "ext, typeHandler=com.zzjr.data.mybatis.handler.JsonMapTypeHandler")
    private Map<String, Object> ext;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(value = "created_at",el = "createdAt, typeHandler = com.zzjr.data.mybatis.handler.LocalDateTimeTypeHandler", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

}
