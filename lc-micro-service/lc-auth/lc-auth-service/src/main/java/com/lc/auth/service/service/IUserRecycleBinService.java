package com.lc.auth.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.auth.service.entity.User;
import com.lc.auth.service.entity.UserRecycleBin;
import com.lc.exception.ServiceException;

/**
 * <p>
 * 用户回收站 服务类
 * </p>
 *
 * @author liucheng
 * @since 2019-05-05
 */
public interface IUserRecycleBinService extends IService<UserRecycleBin> {

    /**
     * 还原
     *
     * @param id Long
     * @throws ServiceException 业务异常
     */
    User restore(Long id) throws ServiceException;

}
