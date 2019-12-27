package com.lc.admin.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 包含乐观锁的基础字段
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "扩展字段")
    private Map<String, Object> ext;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "修改时间", hidden = true)
    private LocalDateTime lastModifiedAt;

    @ApiModelProperty(value = "版本号", hidden = true)
    private Long version;

    @ApiModelProperty(value = "创建人", hidden = true)
    private String createdBy;

    @ApiModelProperty(value = "修改人", hidden = true)
    private String lastModifiedBy;

}
