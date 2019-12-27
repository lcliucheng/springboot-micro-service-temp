package com.lc.admin.service.cms.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.admin.domain.entity.cms.Dict;
import com.lc.admin.repository.cms.DictRepository;
import com.lc.admin.service.cms.IDictService;
import com.lc.enums.common.YesOrNo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
@Service
public class DictServiceImpl
        extends ServiceImpl<DictRepository, Dict>
        implements IDictService {

    @Override
    public List<Dict> list(Wrapper<Dict> queryWrapper) {
        return super.list(queryWrapper);
    }

    @Override
    public List<Dict> findByCode(String code) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq("code", code);
        queryWrapper.eq("is_disabled", YesOrNo.NO.getValue());
        queryWrapper.eq("is_deleted", YesOrNo.NO.getValue());
        queryWrapper.orderByAsc("sort_no");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Dict findOneByCode(String code) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<> ();
        queryWrapper.eq("code", code);
        queryWrapper.eq("is_disabled", YesOrNo.NO.getValue());
        queryWrapper.eq("is_deleted", YesOrNo.NO.getValue());
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean save(Dict entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Dict entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean update(Dict entity, Wrapper<Dict> updateWrapper) {
        return super.update(entity, updateWrapper);
    }

}
