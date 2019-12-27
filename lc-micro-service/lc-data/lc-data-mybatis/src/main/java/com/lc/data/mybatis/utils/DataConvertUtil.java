package com.lc.data.mybatis.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.lc.consts.common.CommonConsts;
import com.lc.data.mybatis.domain.dto.PageDTO;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 *  数据转换工具类
 * @author liucheng
 * @since 2019/11/8 10:33
 */
public class DataConvertUtil {

    /**
     *  将map转换为对象
     * @param map map数据
     * @param clazz 对象类型
     * @return T
     */
    /*public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        if (Objects.isNull(map)) {
            return null;
        }
        return BeanUtil.mapToBean(map, clazz,true);
    }*/

    /**
     *  将list中的map转换为对象
     * @param maps map数据
     * @param clazz 对象类型
     * @return java.util.List<T>
     */
//    public static <T> List<T> mapsToBeansList(List<Map<String, Object>> maps, Class<T> clazz) {
//        if (CollectionUtils.isEmpty(maps)) {
//            return Collections.emptyList();
//        }
//        return maps.stream().map(e -> BeanUtil.mapToBean(e, clazz,true)).collect(toList());
//    }

    /**
     *  将page中的map转换为对象
     * @param mapsPage 分页map数据
     * @param clazz 对象类型
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
//    public static <T> IPage<T> mapsToBeansPage(IPage<Map<String, Object>> mapsPage, Class<T> clazz) {
//        IPage<T> page = new Page<>();
//        BeanUtil.copyProperties(mapsPage,page);
//        if (CollectionUtils.isEmpty(mapsPage.getRecords())) {
//            return page;
//        }
//        page.setRecords(mapsToBeansList(mapsPage.getRecords(), clazz));
//        return page;
//    }

    /**
     *  将page中的map转换为PageDTO对象
     * @param mapsPage 分页map数据
     * @param clazz 对象类型
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     */
//    public static <T> PageDTO<T> mapsToBeansPageDto(IPage<Map<String, Object>> mapsPage, Class<T> clazz) {
//        PageDTO<T> page = new PageDTO<>();
//        BeanUtil.copyProperties(mapsPage,page);
//        if (CollectionUtils.isEmpty(mapsPage.getRecords())) {
//            return page;
//        }
//        page.setRecords(mapsToBeansList(mapsPage.getRecords(), clazz));
//        return page;
//    }

    /**
     *  将list中的map转换为对象
     * @param objs list数据
     * @param clazz 对象类型
     * @return java.util.List<T>
     */
    public static <T,E> List<T> listToListDto(List<E> objs, Class<T> clazz) {
        if (CollectionUtils.isEmpty(objs)) {
            return Collections.emptyList();
        }
        return objs.stream().map(e -> BeanUtil.toBean(e, clazz)).collect(toList());
    }

    /**
     * 将page中的对象转为PageDTO对象
     * @param objPage 分页对象数据
     * @param clazz 对象类型
     * @return com.lc.dao.dto.PageDTO<T>
     */
    public static <T,E> PageDTO<T> pageToPageDto(IPage<E> objPage, Class<T> clazz) {
        PageDTO<T> page = new PageDTO<>();
        BeanUtil.copyProperties(objPage,page);
        page.setPages(objPage.getPages());
        if (CollectionUtils.isEmpty(objPage.getRecords())) {
            return page;
        }
        page.setRecords(objPage.getRecords().stream().map(e -> BeanUtil.toBean(e,clazz)).collect(Collectors.toList()));
        return page;
    }

    /**
     *  将实体对象转为DTO对象
     * @param e 实体队形
     * @param clazz 对象类型
     * @return T
     */
    public static <T,E>T objToObjDto(E e, Class<T> clazz) {
        if(Objects.isNull(e)) {
            return ReflectUtil.newInstance(clazz);
        }
        return BeanUtil.toBean(e,clazz);
    }

    /**
     *  将实体对象转为DTO对象
     * @param objs list数据
     * @param clazz 对象类型
     * @return T
     */
    public static <T,E> List<T> objListToObjDtoList(List<E> objs, Class<T> clazz) {
        if (CollectionUtils.isEmpty(objs)) {
            return Collections.emptyList();
        }
        return objs.stream().map(e -> BeanUtil.toBean(e, clazz)).collect(toList());
    }

    public static final List<Class<?>> COMPLEX_FILED_TYPE = Lists.newArrayList();

    static {
        COMPLEX_FILED_TYPE.add(LocalDateTime.class);
    }

    public static <T,E>T toBean(E e, Class<T> clazz) {
        Field[] fields = clazz.getFields();
        final T target = ReflectUtil.newInstance(clazz);
        List<String> ignoredFiled = Lists.newArrayList();
        for(Field field : fields) {
            if(COMPLEX_FILED_TYPE.contains(field.getType())) {
                ignoredFiled.add(field.getName());
                Object fieldValue = ReflectUtil.getFieldValue(e, field);
                if(Objects.nonNull(fieldValue)) {
                    if(field.getType() == LocalDateTime.class) {
                        ReflectUtil.setFieldValue(e, field, LocalDateTime.parse(fieldValue.toString(), CommonConsts.DATE_TIME_MILLI_FORMATTER));
                    }
//                    if(field.getType() == DataSecurityType.class) {
//                        ReflectUtil.setFieldValue(e, field.get, dataSecurityType.setVal(Aes.getInstance().decrypt(columnValue)););
//                    }
                }
            }
        }
        //忽略特殊字段
        BeanUtil.copyProperties(e, target, ignoredFiled.toArray(new String[ignoredFiled.size()]));
        return null;
    }

}
