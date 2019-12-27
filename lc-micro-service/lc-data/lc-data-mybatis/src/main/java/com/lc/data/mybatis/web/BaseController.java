package com.lc.data.mybatis.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.data.mybatis.query.QueryParamRequest;
import com.lc.data.mybatis.query.QueryWrapperHelper;
import com.lc.data.mybatis.utils.ParamUtil;
import com.lc.validator.group.FindGroup;
import com.lc.validator.group.InsertGroup;
import com.lc.validator.group.UpdateGroup;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 基础控制器
 *
 * @author liucheng
 * @since 2019-12-03
 **/
public abstract class BaseController<S extends IService<T>, T, ID extends Serializable> {

    private static final String RANGE_PARAM_NAME = "range";
    private static final String IN_PARAM_NAME = "in";
    private static final String JSON_PARAM_NAME = "json";
    private static final String SORT_PARAM_NAME = "sort";
    private static final String SECURITY_PARAM_NAME = "security";
    private static final String APPLY_PARAM_NAME = "apply";

    protected final S baseService;

    public BaseController(final S baseService) {
        this.baseService = baseService;
    }

    /**
     * 新增记录
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "新增记录")
    public T insert(@RequestBody @Validated(InsertGroup.class) T t) {
        Field[] fields = ReflectUtil.getFields(t.getClass());
        for (Field field : fields) {
            if (!Objects.isNull(field.getAnnotation(TableId.class))
                    && Objects.isNull(ReflectUtil.getFieldValue(t, field))) {
                Class<?> type = field.getType();
                if (type == Long.class) {
                    ReflectUtil.setFieldValue(t, field, IdWorker.getId());
                } else if (type == String.class) {
                    ReflectUtil.setFieldValue(t, field, IdWorker.getIdStr());
                }
            }
        }

        boolean result = baseService.save(t);
        if (!result) {
            return null;
        }
        return t;
    }

    /**
     * 修改记录
     *
     * @param id 主键
     * @param t  T
     * @return Response
     */
    @ApiOperation(value = "修改记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path")})
    public T update(@PathVariable ID id, @RequestBody @Validated(UpdateGroup.class) T t) {
        T entity = baseService.getById(id);
        if (Objects.isNull(entity)) {
            return null;
        }

        //给主键跟版本号赋值
        Field[] fields = ReflectUtil.getFields(t.getClass());
        for (Field field : fields) {
            if (!Objects.isNull(field.getAnnotation(TableId.class))
                    || !Objects.isNull(field.getAnnotation(Version.class))) {
                Object value = ReflectUtil.getFieldValue(entity, field);
                if (!Objects.isNull(value)) {
                    ReflectUtil.setFieldValue(t, field, value);
                } else {
                    ReflectUtil.setFieldValue(t, field, 0);
                }
            }
        }

        boolean result = baseService.updateById(t);
        if (!result) {
            return null;
        }
        BeanUtil.copyProperties(t,entity, CopyOptions.create().setIgnoreNullValue(true));
        return entity;
    }

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return Response
     */
    @ApiOperation(value = "根据主键查询记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path")})
    public T get(@PathVariable ID id) {
        T entity = baseService.getById(id);
        if (Objects.isNull(entity)) {
            return null;
        }
        return entity;
    }

    /**
     * 删除记录
     *
     * @param id 主键
     * @return Response
     */
    @ApiOperation(value = "删除记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path")})
    public ID deleteById(@PathVariable ID id) {
        boolean result = baseService.removeById(id);
        if (!result) {
            return null;
        }
        return id;
    }

    /**
     * 查询单条记录
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "查询单条记录")
    public T findOne(@Validated(FindGroup.class) T t,
                               @ApiIgnore @RequestParam MultiValueMap<String, String> multiValueMap) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(t,multiValueMap);
        T one = baseService.getOne(queryWrapper);
        return one;
    }

    /**
     * 查询记录
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "查询记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "排序“id,desc”", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "range", value = "createdAt,DATE_TIME,2019-12-22 10:10:10,2019-12-23 10:10:10", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "in", value = "id,LONG,1,2,3...", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "json", value = "ext,$.id,STRING,0,1", allowMultiple = true, paramType = "query")})
    public List<T> findAll(@Validated(FindGroup.class) T t, @ApiIgnore QueryParamRequest param) {
        MultiValueMap<String, String> multiValueMap = ParamUtil.dealQueryParam(param);
        QueryWrapper<T> queryWrapper = getQueryWrapper(t, multiValueMap);
        return baseService.list(queryWrapper);
    }

    /**
     * 分页查询记录
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "分页查询记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sort", value = "id,desc", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "range", value = "createdAt,DATE_TIME,2019-12-22 :10:10:10,2019-12-23 :10:10:10", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "in", value = "id,LONG,1,2,3...", allowMultiple = true, paramType = "query"),
            @ApiImplicitParam(name = "json", value = "ext,$.id,STRING,0,1", allowMultiple = true, paramType = "query")})
    public IPage<T> findPage(@Validated(FindGroup.class) T t, @ApiIgnore QueryParamRequest param) {
        MultiValueMap<String, String> multiValueMap = ParamUtil.dealQueryParam(param);
        QueryWrapper<T> queryWrapper = getQueryWrapper(t, multiValueMap);
        return baseService.page(new Page<>(param.getPage(), param.getSize()), queryWrapper);
    }

    /**
     * 根据条件统计记录数
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "根据条件统计记录数")
    public Integer count(@Validated(FindGroup.class) T t,
                                   @ApiIgnore @RequestParam MultiValueMap<String, String> multiValueMap) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(t,multiValueMap);
        return baseService.count(queryWrapper);
    }

    /**
     * 根据条件统判断记录是否存在
     *
     * @param t T
     * @return Response
     */
    @ApiOperation(value = "根据条件统判断记录是否存在")
    public Boolean exists(@Validated(FindGroup.class) T t) {
        QueryWrapper<T> queryWrapper = getQueryWrapper(t);
        return baseService.count(queryWrapper) > 0;
    }

    /**
     * 获取查询包装对象
     *
     * @param t 实体
     * @return QueryWrapper
     */
    protected <T> QueryWrapper<T> getQueryWrapper(T t) {
        return QueryWrapperHelper.getQueryWrapper(t);
    }

    /**
     * 获取查询包装对象
     *
     * @param t                实体
     * @param multiValueParams 多值请求参数
     * @return QueryWrapper
     */
    protected QueryWrapper<T> getQueryWrapper(T t, MultiValueMap<String, String> multiValueParams) {
        try {
            QueryWrapper<T> queryWrapper = getQueryWrapper(t);
            QueryWrapperHelper.wrapSecurityQueryWrapper(queryWrapper, multiValueParams.get(SECURITY_PARAM_NAME));
            QueryWrapperHelper.wrapRangeQueryWrapper(queryWrapper, multiValueParams.get(RANGE_PARAM_NAME));
            QueryWrapperHelper.wrapInQueryWrapper(queryWrapper, multiValueParams.get(IN_PARAM_NAME));
            QueryWrapperHelper.wrapJsonQueryWrapper(queryWrapper, multiValueParams.get(JSON_PARAM_NAME));
            QueryWrapperHelper.wrapSortQueryWrapper(queryWrapper, multiValueParams.get(SORT_PARAM_NAME));
            QueryWrapperHelper.wrapApplyQueryWrapper(queryWrapper,multiValueParams.get(APPLY_PARAM_NAME));
            return queryWrapper;
        } finally {
            QueryWrapperHelper.removeFields();
        }
    }

    /**
     *  获取排序sql
     * @param multiValueParams 请求参数
     * @return java.lang.String 排序sql
     */
    protected  String getSortSql(MultiValueMap<String, String> multiValueParams) {
        String sortSql = QueryWrapperHelper.wrapSortQueryString(multiValueParams.get(SORT_PARAM_NAME));
        multiValueParams.remove(SORT_PARAM_NAME);
        return sortSql;
    }

    /**
     * 获取关联查询--别名查询包装对象
     *
     * @param t                实体
     * @param multiValueParams 多值请求参数
     * @return QueryWrapper
     */
    protected <E> QueryWrapper<E> getAliasQueryWrapper(E t, MultiValueMap<String, String> multiValueParams, String alias) {
        try {
            QueryWrapper<E> queryWrapper = getQueryWrapper(t);
            QueryWrapperHelper.wrapAliasQueryWrapper(queryWrapper, alias);
            QueryWrapperHelper.wrapSecurityQueryWrapper(queryWrapper, multiValueParams.get(SECURITY_PARAM_NAME));
            QueryWrapperHelper.wrapRangeQueryWrapper(queryWrapper, multiValueParams.get(RANGE_PARAM_NAME));
            QueryWrapperHelper.wrapInQueryWrapper(queryWrapper, multiValueParams.get(IN_PARAM_NAME));
            QueryWrapperHelper.wrapJsonQueryWrapper(queryWrapper, multiValueParams.get(JSON_PARAM_NAME));
            QueryWrapperHelper.wrapSortQueryWrapper(queryWrapper, multiValueParams.get(SORT_PARAM_NAME));
            QueryWrapperHelper.wrapApplyQueryWrapper(queryWrapper,multiValueParams.get(APPLY_PARAM_NAME));
            return queryWrapper;
        } finally {
            QueryWrapperHelper.removeFields();
        }
    }


}
