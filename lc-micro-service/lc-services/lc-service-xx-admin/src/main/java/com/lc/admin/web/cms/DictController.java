package com.lc.admin.web.cms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lc.admin.domain.dto.cms.DictDTO;
import com.lc.admin.domain.entity.cms.Dict;
import com.lc.admin.domain.request.cms.DictRequest;
import com.lc.admin.service.cms.IDictService;
import com.lc.data.mybatis.domain.dto.PageDTO;
import com.lc.data.mybatis.query.QueryParamRequest;
import com.lc.data.mybatis.utils.DataConvertUtil;
import com.lc.data.mybatis.web.BaseController;
import com.lc.exception.ErrorCode;
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
import java.util.Map;

/**
 * 产品分类接口相关操作
 *
 * @author yanghy
 * @date 2019/4/30
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@RestController
@RequestMapping("/dicts")
@Api(tags = "数据字典接口")
@Validated
public class DictController extends BaseController<IDictService, Dict,Long> {

    public DictController(IDictService baseService) {
        super(baseService);
    }

    /**
     * 查询全部数据字典
     *
     * @return Response
     */
    @GetMapping(value = "/all")
    @ApiOperation(value = "查询全部数据字典")
    public Response<Map<String, List<DictDTO>>> getAll() {
        List<Dict> dictAll = baseService.list();
        Map<String, List<DictDTO>> map = Maps.newHashMap();
        List<DictDTO> data = DataConvertUtil.listToListDto(dictAll,DictDTO.class);
        if (data != null && !data.isEmpty()) {
            data.forEach(item -> {
                if (map.containsKey(item.getCode())) {
                    item.setItemValue(item.getValue());
                    List<DictDTO> list = map.get(item.getCode());
                    list.add(item);
                } else {
                    List<DictDTO> list = Lists.newArrayList();
                    item.setItemValue(item.getValue());
                    list.add(item);
                    map.put(item.getCode(), list);
                }
            });
        }

        return Response.success(map);
    }

    /**
     * 根据字典名称修改字典值
     *
     * @param code  字典名称
     * @param value 字典值
     * @return Response
     */
    @PostMapping(value = "/updateByCode/{code}")
    @ApiOperation(value = "根据字典名称修改字典值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典名称", paramType = "path")})
    public Response<DictDTO> updateByCode(@PathVariable String code, @RequestParam("value") String value) {

        List<Dict> dicts = baseService.findByCode(code);
        if (CollectionUtil.isEmpty(dicts)) {
            return Response.failed(ErrorCode.DATA_NON_EXISTENT);
        }

        Dict dict = dicts.get(0);
        dict.setValue(value);
        boolean result = baseService.updateById(dict);
        if (!result) {
            return Response.failed(ErrorCode.DATA_OPERATE_EXCEPTION);
        }
        return Response.success(DataConvertUtil.objToObjDto(dict,DictDTO.class));
    }

    /**
     * 根据字典名称查询字典列表
     *
     * @param code 字典名称
     * @return Response
     */
    @GetMapping(value = "/findByCode/{code}")
    @ApiOperation(value = "根据字典名称修改字典值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "字典名称", paramType = "path")})
    public Response<List<DictDTO>> findByCode(@PathVariable String code) {
        return Response.success(DataConvertUtil.listToListDto(baseService.findByCode(code),DictDTO.class));
    }


    /**
     * 查询数据字典
     *
     * @return Response
     */
    @GetMapping
    @ApiOperation(value = "查询数据字典")
    public Response<PageDTO<DictDTO>> findPage(@Validated(FindGroup.class) DictRequest request,
                                               @ApiIgnore QueryParamRequest param) {
        Dict dict = new Dict();
        BeanUtil.copyProperties(dict,request);
        param.setSort("code,asc");
        return Response.success(DataConvertUtil.pageToPageDto(super.findPage(dict,param),DictDTO.class));
    }

    /**
     * 根据ID查询数据字典
     *
     * @return Response
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询数据字典")
    public Response<DictDTO> findById(@Validated(FindGroup.class) @PathVariable Long id) {

        return Response.success(DataConvertUtil.objToObjDto(super.get(id),DictDTO.class));
    }

    /**
     * 数据字典
     *
     * @return Response
     */
    @PostMapping
    @ApiOperation(value = "数据字典")
    public Response<DictDTO> insert(@Validated(InsertGroup.class) @RequestBody DictRequest request) {
        Dict dict = new Dict();
        BeanUtil.copyProperties(request, dict);
        if(request.isGenerateId()){
            long id = IdWorker.getId();
            dict.setId(id);
            dict.setValue(String.valueOf(id));
        }
        return Response.success(DataConvertUtil.objToObjDto(super.insert(dict),DictDTO.class));
    }

    /**
     * 根据ID修改数据字典
     *
     * @return Response
     */
    @PutMapping(value = "/{id}")
    @ApiOperation(value = "根据ID修改数据字典")
    public Response<DictDTO> update(@Validated(UpdateGroup.class) @PathVariable Long id,
                                    @RequestBody @Validated(UpdateGroup.class) DictRequest request) {

        Dict dict = new Dict();
        BeanUtil.copyProperties(request, dict);
        return Response.success(DataConvertUtil.objToObjDto(super.update(id,dict),DictDTO.class));
    }


}
