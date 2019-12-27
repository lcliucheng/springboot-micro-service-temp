package com.lc.admin.service.cms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.admin.domain.entity.cms.Dict;

import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author liucheng
 * @since 2019-12-05
 */
public interface IDictService extends IService<Dict> {

    /**
     * 根据字典编号查询
     *
     * @param code String
     * @return List<Dict>
     */
    List<Dict> findByCode(String code);

    /**
     * 根据字典编号查询单条数据
     *
     * @param code String
     * @return List<Dict>
     */
    Dict findOneByCode(String code);

}
