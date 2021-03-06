package com.lc.admin.repository;

import com.baomidou.dynamic.datasource.annotation.DS;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.admin.domain.entity.User;
import com.lc.data.mybatis.constants.DataSourceType;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
@DS(DataSourceType.ADMIN)
public interface AdminUserRepository extends BaseMapper<User> {
}
