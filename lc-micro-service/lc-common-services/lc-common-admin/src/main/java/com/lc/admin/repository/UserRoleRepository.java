package com.lc.admin.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.admin.domain.entity.UserRole;
import com.lc.data.mybatis.constants.DataSourceType;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
@DS(DataSourceType.ADMIN)
public interface UserRoleRepository extends BaseMapper<UserRole> {
}
