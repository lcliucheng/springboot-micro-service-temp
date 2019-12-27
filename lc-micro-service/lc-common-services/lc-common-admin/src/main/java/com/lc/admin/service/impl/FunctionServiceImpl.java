package com.lc.admin.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.admin.domain.entity.Function;
import com.lc.admin.repository.FunctionRepository;
import com.lc.admin.service.IFunctionService;
import com.lc.enums.common.YesOrNo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-23
 */
@Service
@DS("admin")
public class FunctionServiceImpl
        extends ServiceImpl<FunctionRepository, Function>
        implements IFunctionService {

    @Override
    public List<Function> list() {
        Function function = new Function();
        function.setOffline(YesOrNo.NO.getValue());
        function.setDeleted(YesOrNo.NO.getValue());

        QueryWrapper<Function> queryWrapper = new QueryWrapper<>(function);
        queryWrapper.orderByAsc("sort_no");
        return baseMapper.selectList(queryWrapper);
    }

}
