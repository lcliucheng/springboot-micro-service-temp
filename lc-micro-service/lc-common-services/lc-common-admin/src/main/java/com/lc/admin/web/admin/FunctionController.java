package com.lc.admin.web.admin;


import com.lc.admin.domain.entity.Function;
import com.lc.admin.service.IFunctionService;
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
 * <p> 资源管理 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Slf4j
@RestController
@RequestMapping("/functions")
@Api(tags = "资源管理")
public class FunctionController extends BaseController<IFunctionService, Function, Long> {

    public FunctionController(final IFunctionService functionService) {
        super(functionService);
    }

    /**
     * 分页查询资源
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "分页查询资源")
    public Response<PageDTO<Function>> page(@Validated(FindGroup.class) Function request,
                                            @ApiIgnore QueryParamRequest param) {
        return Response.success(DataConvertUtil.pageToPageDto(findPage(request, param),Function.class));
    }

    /**
     * 根据ID查询资源信息
     *
     * @return Response
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询资源信息")
    public Response<Function> findById(@Validated(FindGroup.class) @PathVariable Long id) {
        return Response.success(super.get(id));
    }

    /**
     * 保存资源信息
     *
     * @return Response
     */
    @PostMapping
    @ApiOperation(value = "保存资源信息")
    public Response<Function> save(@Validated(InsertGroup.class) @RequestBody Function request) {
        return Response.success(super.insert(request));
    }

    /**
     * 根据ID修改资源信息
     *
     * @return Response
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "根据ID修改资源信息")
    public Response<Function> updateById(@Validated(UpdateGroup.class) @PathVariable Long id,
                                       @RequestBody @Validated(UpdateGroup.class) Function request) {
        return Response.success(super.update(id,request));
    }

    /**
     * 根据ID删除资源信息
     *
     * @return Response
     */
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "根据ID删除资源信息")
    public Response<Long> delete(@Validated(UpdateGroup.class) @PathVariable Long id) {
        return Response.success(super.deleteById(id));
    }

}
