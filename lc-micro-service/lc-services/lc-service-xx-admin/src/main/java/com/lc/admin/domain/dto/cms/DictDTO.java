package com.lc.admin.domain.dto.cms;

import com.lc.admin.domain.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 数据字典传输对象
 *
 * @author yanghy
 * @since 2019-12-24
 */

@ApiModel(value = "DictDTO", description = "数据字典传输对象")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "值")
    private String itemValue;

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

}
