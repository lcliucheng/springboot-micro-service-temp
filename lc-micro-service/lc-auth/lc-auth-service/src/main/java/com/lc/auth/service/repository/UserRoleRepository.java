package com.lc.auth.service.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.auth.service.entity.UserRole;
import com.lc.data.mybatis.constants.DataSourceType;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author liucheng
 * @since 2019-05-05
 */
@DS(DataSourceType.ADMIN)
public interface UserRoleRepository extends BaseMapper<UserRole> {
}
