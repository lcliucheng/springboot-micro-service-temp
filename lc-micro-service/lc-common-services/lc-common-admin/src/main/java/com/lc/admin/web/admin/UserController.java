package com.lc.admin.web.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lc.admin.consts.UserType;
import com.lc.admin.domain.entity.*;
import com.lc.admin.domain.request.user.RegisterUserRequest;
import com.lc.admin.domain.request.user.ResetUserPasswordRequest;
import com.lc.admin.domain.request.user.UpdateUserPasswordRequest;
import com.lc.admin.domain.vo.MenuVO;
import com.lc.admin.repository.UserRoleRepository;
import com.lc.admin.security.SecurityUserDetails;
import com.lc.admin.service.IAdminUserService;
import com.lc.admin.service.IFunctionService;
import com.lc.admin.service.IMenuService;
import com.lc.admin.service.IRoleService;
import com.lc.common.AuthContext;
import com.lc.common.annotation.Signed;
import com.lc.data.mybatis.domain.dto.PageDTO;
import com.lc.data.mybatis.query.QueryParamRequest;
import com.lc.data.mybatis.utils.DataConvertUtil;
import com.lc.data.mybatis.web.BaseController;
import com.lc.enums.common.YesOrNo;
import com.lc.exception.ErrorCode;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> 用户管理 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@SuppressWarnings({"Duplicates","SpringJavaInjectionPointsAutowiringInspection"})
@Slf4j
@RestController("adminUserController")
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController extends BaseController<IAdminUserService, User, Long> {

    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final IMenuService menuService;
    private final IFunctionService functionService;
    private final UserRoleRepository userRoleRepository;

    public UserController(final IAdminUserService userService,
                          final IRoleService roleService,
                          final PasswordEncoder passwordEncoder,
                          final IMenuService menuService,
                          final IFunctionService functionService,
                          final UserRoleRepository userRoleRepository) {
        super(userService);
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.menuService = menuService;
        this.functionService = functionService;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * 分页查询用户
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "分页查询用户")
    public Response<PageDTO<User>> page(@Validated(FindGroup.class) User request,
                                        @ApiIgnore QueryParamRequest param) {
        return Response.success(DataConvertUtil.pageToPageDto(findPage(request, param),User.class));
    }

    /**
     * 根据ID查询用户信息
     *
     * @return Response
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询用户信息")
    public Response<User> findById(@Validated(FindGroup.class) @PathVariable Long id) {
        return Response.success(super.get(id));
    }

    /**
     * 保存用户信息
     *
     * @return Response
     */
    @PostMapping
    @ApiOperation(value = "保存用户信息")
    public Response<User> save(@Validated(InsertGroup.class) @RequestBody User request) {
        return Response.success(super.insert(request));
    }

    /**
     * 根据ID修改用户信息
     *
     * @return Response
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "根据ID修改用户信息")
    public Response<User> updateById(@Validated(UpdateGroup.class) @PathVariable Long id,
                                     @RequestBody @Validated(UpdateGroup.class) User request) {
        return Response.success(super.update(id,request));
    }

    /**
     * 根据ID删除用户信息
     *
     * @return Response
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据ID删除用户信息")
    public Response<Long> delete(@Validated(UpdateGroup.class) @PathVariable Long id) {
        return Response.success(super.deleteById(id));
    }

    /**
     * 获取用户信息
     *
     * @return Response
     */
    @Signed
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息")
    public Response<User> getUserInfo() {
        Long userId = Long.valueOf(AuthContext.getJwtToken().getUserId());
        User user = baseService.getById(userId);
        if (Objects.isNull(user)) {
            return Response.failed(ErrorCode.DATA_NON_EXISTENT);
        }

        user.setPassword("******");
        user.setSalt("******");
        return Response.success(user);
    }

    /**
     * 获取用户菜单
     *
     * @return Response
     */
    @Signed
    @GetMapping("/getUserMenus")
    @ApiOperation(value = "获取用户菜单")
    public Response<List<MenuVO>> getUserMenu() {
        List<UserRole> roles = baseService.findRoles(Long.valueOf(AuthContext.getJwtToken().getUserId()));
        Map<Long, Menu> menuMap = menuService.list().stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
        Set<Menu> menus = Sets.newHashSet();
        if (CollectionUtil.isNotEmpty(roles)) {
            for (UserRole userRole : roles) {
                List<RoleMenu> roleMenus = roleService.findMenus(userRole.getRoleId());
                roleMenus.forEach(roleMenu -> menus.add(menuMap.get(roleMenu.getMenuId())));
            }
        }

        List<Menu> collect = menuMap.values().stream().filter(item -> item.getHidden() == 1).collect(Collectors.toList());
        menus.addAll(collect);

        List<MenuVO> menuVos = menus.stream().filter(m -> !Objects.isNull(m) && m.getHidden() != 2).map(Menu::convert).sorted(Comparator.comparing(MenuVO::getSortNo)).collect(Collectors.toList());

        Map<Long, List<MenuVO>> menuGroup = menuVos.stream().collect(Collectors.groupingBy(MenuVO::getParentId));
        menuVos.forEach(m -> m.setChildren(CollectionUtil.isEmpty(menuGroup.get(m.getId())) ? null : menuGroup.get(m.getId())));
        return Response.success(menuVos.stream().filter(m -> m.getLevel() == 1).collect(Collectors.toList()));
    }

    /**
     * 获取用户资源
     *
     * @return Response
     */
    @Signed
    @GetMapping("/getUserFunctions")
    @ApiOperation(value = "获取用户资源")
    public Response<List<Function>> getUserFunctions() {
        List<UserRole> roles = baseService.findRoles(Long.valueOf(AuthContext.getJwtToken().getUserId()));
        Map<Long, Function> functionMap = functionService.list().stream().collect(Collectors.toMap(Function::getId, function -> function));
        Set<Function> functions = Sets.newHashSet();
        if (CollectionUtil.isNotEmpty(roles)) {
            for (UserRole userRole : roles) {
                List<RoleFunction> roleMenus = roleService.findFunctions(userRole.getRoleId());
                roleMenus.forEach(roleMenu -> {
                    Function function = functionMap.get(roleMenu.getFunctionId());
                    if(Objects.nonNull(function)) {
                        functions.add(function);
                    }
                });
            }
        }
        return Response.success(Lists.newArrayList(functions));
    }

    /**
     * 注册用户
     *
     * @param registerUserRequest 注册用户参数
     * @return Response
     */
    @PostMapping("/register")
    @ApiOperation(value = "注册用户")
    public Response<User> register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        User sysUser = new User();
        BeanUtil.copyProperties(registerUserRequest,sysUser);
        if(StrUtil.isNotBlank(registerUserRequest.getPassword())){
            sysUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        }
        sysUser.setDisabled(YesOrNo.NO.getValue());
        User insert = super.insert(sysUser);

        if(Objects.equals(UserType.CHANNEL.getValue(),registerUserRequest.getType())){
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("name","channel");
            Role channelRole = roleService.getOne(wrapper);
            UserRole userRole = new UserRole();
            userRole.setUserId(insert.getId());
            userRole.setRoleId(channelRole.getId());
            userRoleRepository.insert(userRole);

        }
        return Response.success(insert);
    }

    /**
     * 修改用户信息
     *
     * @param request 修改用户信息
     * @return Response
     */
    @PutMapping("/update/{id}")
    @ApiOperation(value = "修改用户信息")
    public Response<Long> updatePassword(@PathVariable Long id,@RequestBody @Valid RegisterUserRequest request) {
        User user = baseService.getById(id);
        if (Objects.isNull(user)) {
            return Response.failed(ErrorCode.DATA_NON_EXISTENT);
        }

        BeanUtil.copyProperties(request,user, CopyOptions.create().setIgnoreNullValue(true));
        if(StrUtil.isNotBlank(request.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        boolean result = baseService.updateById(user);

        if(result){
            return Response.success();
        }else{
            return Response.failed("更新失败");
        }
    }

    /**
     * 修改用户密码
     *
     * @param updateUserPasswordRequest 修改用户密码参数
     * @return Response
     */
    @PutMapping("/password/update")
    @ApiOperation(value = "修改用户密码")
    public Response<Long> updatePassword(@RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest) {
        User user = baseService.getById(updateUserPasswordRequest.getUserId());
        if (Objects.isNull(user)) {
            return Response.failed(ErrorCode.DATA_NON_EXISTENT);
        }

        if (!passwordEncoder.matches(updateUserPasswordRequest.getOriginalPassword(), user.getPassword())) {
            return Response.failed(ErrorCode.BIZ_EXCEPTION.getValue(),"原密码错误");
        }

        user.setPassword(passwordEncoder.encode(updateUserPasswordRequest.getPassword()));
        baseService.updateById(user);
        return Response.success(updateUserPasswordRequest.getUserId());
    }

    /**
     * 重置用户密码
     *
     * @param resetUserPasswordRequest 重置用户密码参数
     * @return Response
     */
    @PutMapping("/password/reset")
    @ApiOperation(value = "重置用户密码")
    public Response<Long> resetPassword(@RequestBody @Valid ResetUserPasswordRequest resetUserPasswordRequest) {
        User user = baseService.getById(resetUserPasswordRequest.getUserId());
        if (Objects.isNull(user)) {
            return Response.failed(ErrorCode.DATA_NON_EXISTENT);
        }

        user.setPassword(passwordEncoder.encode(resetUserPasswordRequest.getPassword()));
        baseService.save(user);
        return Response.success(resetUserPasswordRequest.getUserId());
    }

    /**
     * 回收
     *
     * @param id Long
     * @return Response
     */
    @DeleteMapping(value = "/recovery/{id}")
    @ApiOperation(value = "回收", notes = "先复制再删除")
    public Response<UserRecycleBin> recovery(@PathVariable Long id) throws ServiceException {
        return Response.success(baseService.recovery(id));
    }

    /**
     * 保存用户角色
     *
     * @param id Long
     * @return Response
     */
    @PostMapping(value = "/roles/{id}")
    @ApiOperation(value = "保存角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path"),
            @ApiImplicitParam(name = "roleIds", value = "角色编号，多个逗号隔开", paramType = "form")})
    public Response<Long> saveRoles(@PathVariable Long id, @RequestParam String roleIds)
            throws ServiceException {
        Set<UserRole> roles = Sets.newHashSet();
        if(StringUtils.isNotBlank(roleIds)){
            String[] roleIdArray = roleIds.split(",");
            for (String roleId : roleIdArray) {
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(Long.valueOf(roleId));
                roles.add(userRole);
            }
        }

        baseService.saveRoles(id, roles);
        return Response.success(id);
    }

    /**
     * 根据主键查询角色
     *
     * @param id Long
     * @return Response
     */
    @GetMapping(value = "/roles/{id}")
    @ApiOperation(value = "根据主键查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", paramType = "path")})
    public Response<List<UserRole>> findByUserId(@PathVariable Long id) throws ServiceException {
        return Response.success(baseService.findRoles(id));
    }

    /**
     * 根据角色取所有用户
     *
     * @return Response
     */
    @GetMapping(value = "/getAll")
    @ApiOperation(value = "获取所有用户")
    public Response<List<User>> getAll() throws ServiceException {
        if(Objects.isNull(AuthContext.getJwtToken())){
            return Response.success();
        }
        SecurityUserDetails userDetails = (SecurityUserDetails) AuthContext.getJwtToken().getUserDetails();
        String type = userDetails.getUser().getType();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(Objects.equals(UserType.CHANNEL.getValue(),type)){
            wrapper.eq("username",userDetails.getUsername());
        }else if(Objects.equals(UserType.BUSI.getValue(),type)){
            wrapper.eq("busi_user_name",userDetails.getUsername());
        }
        wrapper.eq("type","channel");
        return Response.success(baseService.list(wrapper));
    }


}
