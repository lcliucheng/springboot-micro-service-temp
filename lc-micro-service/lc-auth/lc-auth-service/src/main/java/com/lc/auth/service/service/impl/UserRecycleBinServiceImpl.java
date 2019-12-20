package com.lc.auth.service.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.auth.service.entity.User;
import com.lc.auth.service.entity.UserRecycleBin;
import com.lc.auth.service.repository.AdminUserRepository;
import com.lc.auth.service.repository.UserRecycleBinRepository;
import com.lc.auth.service.service.IUserRecycleBinService;
import com.lc.exception.ErrorCode;
import com.lc.exception.ErrorInfo;
import com.lc.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p> 用户回收站业务层 </p>
 *
 * @author liucheng
 * @since 2019-05-16
 */
@Service
@DS("admin")
public class UserRecycleBinServiceImpl
        extends ServiceImpl<UserRecycleBinRepository, UserRecycleBin>
        implements IUserRecycleBinService {

    private final AdminUserRepository userRepository;

    public UserRecycleBinServiceImpl(final AdminUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 还原
     *
     * @param id Long
     * @throws ServiceException 业务异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public User restore(Long id) throws ServiceException {
        UserRecycleBin userRecycleBin = baseMapper.selectById(id);
        if (Objects.isNull(userRecycleBin)) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_NON_EXISTENT.getValue(), "用户回收记录不存在"));
        }

        User user = new User();
        BeanUtil.copyProperties(userRecycleBin, user);
        boolean result = retBool(baseMapper.deleteById(id));
        if (!result) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION.getValue(),
                    "还原异常"));
        }

        result = retBool(userRepository.insert(user));
        if (!result) {
            throw new ServiceException(ErrorInfo.error(ErrorCode.DATA_OPERATE_EXCEPTION.getValue(),
                    "还原异常"));
        }

        return user;
    }

}
