package com.lc.admin.web.admin;

import com.google.common.collect.Sets;
import com.lc.admin.domain.dto.role.RoleMenuFunctionDTO;
import com.lc.admin.domain.entity.Role;
import com.lc.admin.domain.entity.RoleFunction;
import com.lc.admin.domain.entity.RoleMenu;
import com.lc.admin.domain.vo.MenuVO;
import com.lc.admin.service.IMenuService;
import com.lc.admin.service.IRoleService;
import com.lc.data.mybatis.domain.dto.PageDTO;
import com.lc.data.mybatis.query.QueryParamRequest;
import com.lc.data.mybatis.utils.DataConvertUtil;
import com.lc.data.mybatis.web.BaseController;
import com.lc.exception.ServiceException;
import com.lc.response.Response;
import com.lc.validator.group.FindGroup;
import com.lc.validator.group.InsertGroup;
import com.lc.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Set;

/**
 * <p> 角色管理 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Slf4j
@RestController
@RequestMapping("/roles")
@Api(tags = "角色管理")
public class RoleController extends BaseController<IRoleService, Role, Long> {

    private final IMenuService menuService;

    public RoleController(final IRoleService roleService, final IMenuService menuService) {
        super(roleService);
        this.menuService = menuService;
    }

    /**
     * 分页查询角色
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "分页查询角色")
    public Response<PageDTO<Role>> page(@Validated(FindGroup.class) Role request,
                                        @ApiIgnore QueryParamRequest param) {
        return Response.success(DataConvertUtil.pageToPageDto(findPage(request, param),Role.class));
    }

    /**
     * 根据ID查询角色信息
     *
     * @return Response
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询角色信息")
    public Response<Role> findById(@Validated(FindGroup.class) @PathVariable Long id) {
        return Response.success(super.get(id));
    }

    /**
     * 保存角色信息
     *
     * @return Response
     */
    @PostMapping
    @ApiOperation(value = "保存角色信息")
    public Response<Role> save(@Validated(InsertGroup.class) @RequestBody Role request) {
        return Response.success(super.insert(request));
    }

    /**
     * 根据ID修改角色信息
     *
     * @return Response
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "根据ID修改角色信息")
    public Response<Role> updateById(@Validated(UpdateGroup.class) @PathVariable Long id,
                                     @RequestBody @Validated(UpdateGroup.class) Role request) {
        return Response.success(super.update(id,request));
    }

    /**
     * 根据ID删除角色信息
     *
     * @return Response
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据ID删除角色信息")
    public Response<Long> delete(@Validated(UpdateGroup.class) @PathVariable Long id) {
        return Response.success(super.deleteById(id));
    }

    /**
     * 保存角色菜单
     *
     * @param id Long
     * @return Response
     */
    @PostMapping(value = "/menus/{id}")
    @ApiOperation(value = "保存菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path"),
            @ApiImplicitParam(name = "menuIds", value = "菜单编号，多个逗号隔开", paramType = "form")})
    public Response<Long> saveMenus(@PathVariable Long id, @RequestParam String menuIds)
            throws ServiceException {
        Set<RoleMenu> menus = Sets.newHashSet();
        String[] menuIdArray = menuIds.split(",");
        for (String menuId : menuIdArray) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(id);
            roleMenu.setMenuId(Long.valueOf(menuId));
            menus.add(roleMenu);
        }

        baseService.saveMenus(id, menus);
        return Response.success(id);
    }

    /**
     * 根据主键查询菜单
     *
     * @param id Long
     * @return Response
     */
    @GetMapping(value = "/menus/{id}")
    @ApiOperation(value = "根据主键查询菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path")})
    public Response<List<RoleMenu>> findMenuByUserId(@PathVariable Long id) {
        return Response.success(baseService.findMenus(id));
    }

    /**
     * 保存角色资源
     *
     * @param id Long
     * @return Response
     */
    @PostMapping(value = "/functions/{id}")
    @ApiOperation(value = "保存资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path"),
            @ApiImplicitParam(name = "functionIds", value = "资源编号，多个逗号隔开", paramType = "form")})
    public Response<Long> saveFunctions(@PathVariable Long id, @RequestParam("functionIds") String functionIds, @RequestParam("menuIds") String menuIds)
            throws ServiceException {
        Set<RoleFunction> functions = Sets.newHashSet();
        String[] functionIdArray = functionIds.split(",");
        for (String functionId : functionIdArray) {
            RoleFunction roleFunction = new RoleFunction();
            roleFunction.setRoleId(id);
            roleFunction.setFunctionId(Long.valueOf(functionId));
            functions.add(roleFunction);
        }
        //保存用户菜单绑定关系
        Set<RoleMenu> menus = Sets.newHashSet();
        String[] menuIdArray = menuIds.split(",");
        for (String menuId : menuIdArray) {
            RoleMenu sysRoleMenu = new RoleMenu();
            sysRoleMenu.setRoleId(id);
            sysRoleMenu.setMenuId(Long.valueOf(menuId));
            menus.add(sysRoleMenu);
        }
        baseService.saveFunctions(id, functions,menus);
        return Response.success(id);
    }

    /**
     * 根据主键查询资源
     *
     * @param id Long
     * @return Response
     */
    @GetMapping(value = "/functions/{id}")
    @ApiOperation(value = "根据主键查询资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path")})
    public Response<List<RoleFunction>> findFunctionByRoleId(@PathVariable Long id) {
        return Response.success(baseService.findFunctions(id));
    }

    /**
     * 获取菜单树列表
     *
     * @return Response
     */
    @GetMapping(value = "/menus/tree/{roleId}")
    @ApiOperation(value = "获取所有菜单及资源")
    public Response<RoleMenuFunctionDTO> findAllTree(@PathVariable Long roleId) {
        RoleMenuFunctionDTO roleMenuFunction = new RoleMenuFunctionDTO();

        List<MenuVO> menus = menuService.listFunctionTree();
        roleMenuFunction.setMenus(menus);

        List<RoleFunction> roleFunctions = baseService.findFunctions(roleId);
        roleMenuFunction.setRoleFunctions(roleFunctions);

        List<RoleMenu> roleMenus = baseService.findMenus(roleId);
        roleMenuFunction.setRoleMenus(roleMenus);

        return Response.success(roleMenuFunction);
    }

}
