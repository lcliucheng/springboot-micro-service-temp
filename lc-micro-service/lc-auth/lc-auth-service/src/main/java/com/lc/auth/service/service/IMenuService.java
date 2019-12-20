package com.lc.auth.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.auth.service.domain.vo.MenuVO;
import com.lc.auth.service.entity.Menu;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author liucheng
 * @since 2019-05-05
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 查询所有的菜单，组装树结构
     *
     * @return List<MenuVO>
     */
    List<MenuVO> listTree();

    /**
     * 查询所有的菜单、资源，组装树结构
     *
     * @return List<MenuVO>
     */
    List<MenuVO> listFunctionTree();

}
