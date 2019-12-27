package com.lc.admin.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.admin.domain.entity.RoleMenu;
import com.lc.data.mybatis.constants.DataSourceType;

/**
 * <p>
 * 角色菜单 Mapper 接口
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
@DS(DataSourceType.ADMIN)
public interface RoleMenuRepository extends BaseMapper<RoleMenu> {
}
