package com.lc.auth.service.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lc.auth.service.domain.vo.MenuVO;
import com.lc.data.mybatis.domain.BaseFullEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 菜单
 *
 * @author liucheng
 * @since 2019-04-16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Menu对象", description = "菜单")
@TableName("sys_menu")
public class Menu extends BaseFullEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "父主键")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "级别")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "序号")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "组件")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "页面地址")
    @TableField("menu_page")
    private String menuPage;

    @ApiModelProperty(value = "访问路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "重定向路径")
    @TableField("redirect")
    private String redirect;

    @ApiModelProperty(value = "描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty(value = "是否隐藏  0：否 1：是")
    @TableField("is_hidden")
    private Integer hidden;

    @ApiModelProperty(value = "是否下线  0：否 1：是")
    @TableField("is_offline")
    private Integer offline;

    @ApiModelProperty(value = "是否删除  0：否 1：是")
    @TableField("is_deleted")
    private Integer deleted;

    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 转化成MenuVO
     *
     * @return MenuVO
     */
    public MenuVO convert() {
        MenuVO menu = new MenuVO();
        BeanUtil.copyProperties(this, menu);
        return menu;
    }

}
