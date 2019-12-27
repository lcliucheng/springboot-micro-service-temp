package com.lc.admin.domain.request.cms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * 问题反馈
 *
 * @author yanghy
 * @date 2019/05/13
 */

@ApiModel(value = "QuestionRequest", description = "传输对象")
@Data
@Accessors(chain = true)
public class DictRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "排序号")
    private Integer sortNo;

    @ApiModelProperty(value = "是否禁用 0：否 1：是")
    private Integer disabled;

    @ApiModelProperty(value = "是否删除 0：否 1：是")
    private Integer deleted;

    @ApiModelProperty(value = "扩展字段", hidden = true)
    private Map<String, Object> ext;

    @ApiModelProperty(value = "是否设置值为ID")
    private boolean generateId;
}
