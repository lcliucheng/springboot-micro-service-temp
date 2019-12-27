package com.lc.admin.web.admin;

import com.lc.admin.domain.entity.Menu;
import com.lc.admin.service.IMenuService;
import com.lc.data.mybatis.domain.dto.PageDTO;
import com.lc.data.mybatis.query.QueryParamRequest;
import com.lc.data.mybatis.utils.DataConvertUtil;
import com.lc.data.mybatis.web.BaseController;
import com.lc.response.Response;
import com.lc.validator.group.FindGroup;
import com.lc.validator.group.InsertGroup;
import com.lc.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p> 菜单管理 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Slf4j
@RestController
@RequestMapping("/menus")
@Api(tags = "菜单管理")
public class MenuController extends BaseController<IMenuService, Menu, Long> {

    public MenuController(IMenuService menuService) {
        super(menuService);
    }

    /**
     * 分页查询菜单
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "分页查询菜单")
    public Response<PageDTO<Menu>> page(@Validated(FindGroup.class) Menu request,
                                        @ApiIgnore QueryParamRequest param) {
        return Response.success(DataConvertUtil.pageToPageDto(findPage(request, param),Menu.class));
    }

    /**
     * 根据ID查询菜单信息
     *
     * @return Response
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询菜单信息")
    public Response<Menu> findById(@Validated(FindGroup.class) @PathVariable Long id) {
        return Response.success(super.get(id));
    }

    /**
     * 保存菜单信息
     *
     * @return Response
     */
    @PostMapping
    @ApiOperation(value = "保存菜单信息")
    public Response<Menu> save(@Validated(InsertGroup.class) @RequestBody Menu request) {
        return Response.success(super.insert(request));
    }

    /**
     * 根据ID修改菜单信息
     *
     * @return Response
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "根据ID修改菜单信息")
    public Response<Menu> updateById(@Validated(UpdateGroup.class) @PathVariable Long id,
                                         @RequestBody @Validated(UpdateGroup.class) Menu request) {
        return Response.success(super.update(id,request));
    }

    /**
     * 根据ID删除菜单信息
     *
     * @return Response
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据ID删除菜单信息")
    public Response<Long> delete(@Validated(UpdateGroup.class) @PathVariable Long id) {
        return Response.success(super.deleteById(id));
    }
}
