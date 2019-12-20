package com.lc.auth.service.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.auth.service.domain.vo.FunctionVO;
import com.lc.auth.service.domain.vo.MenuVO;
import com.lc.auth.service.entity.Function;
import com.lc.auth.service.entity.Menu;
import com.lc.auth.service.repository.MenuRepository;
import com.lc.auth.service.service.IFunctionService;
import com.lc.auth.service.service.IMenuService;
import com.lc.enums.common.YesOrNo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源 服务实现类
 * </p>
 *
 * @author liucheng
 * @since 2019-05-23
 */
@SuppressWarnings("Duplicates")
@Service
@DS("admin")
public class MenuServiceImpl
        extends ServiceImpl<MenuRepository, Menu>
        implements IMenuService {

    private final IFunctionService functionService;

    public MenuServiceImpl(final IFunctionService functionService) {
        this.functionService = functionService;
    }

    @Override
    public List<Menu> list() {
        Menu menu = new Menu();
        menu.setOffline(YesOrNo.NO.getValue());
        menu.setDeleted(YesOrNo.NO.getValue());
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(menu);
        queryWrapper.orderByAsc("sort_no");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<MenuVO> listTree() {
        List<Menu> menus = list();
        List<MenuVO> menuVos = menus.stream().map(Menu::convert).collect(Collectors.toList());

        Map<Long, List<MenuVO>> menuGroup = menuVos.stream().collect(Collectors.groupingBy(MenuVO::getParentId));
        menuVos.forEach(m -> m.setChildren(menuGroup.get(m.getId())));

        return menuVos.stream().filter(m -> m.getLevel() == 1).collect(Collectors.toList());
    }

    @Override
    public List<MenuVO> listFunctionTree() {
        List<Function> functions = functionService.list();
        Map<Long, List<FunctionVO>> functionGroup = functions.stream().map(Function::convert)
                .collect(Collectors.groupingBy(FunctionVO::getMenuId));

        List<Menu> menus = list();
        List<MenuVO> menuVos = menus.stream().map(Menu::convert).collect(Collectors.toList());
        //过滤掉隐藏菜单
        menuVos = menuVos.stream().filter(item -> item.getHidden() != 1).collect(Collectors.toList());

        menuVos.forEach(m -> m.setFunctions(functionGroup.get(m.getId())));


        Map<Long, List<MenuVO>> menuGroup = menuVos.stream().collect(Collectors.groupingBy(MenuVO::getParentId));
        menuVos.forEach(m -> m.setChildren(menuGroup.get(m.getId())));


        return menuVos.stream().filter(m -> m.getLevel() == 1).collect(Collectors.toList());
    }
}
