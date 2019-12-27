package com.lc.data.mybatis.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.lc.annotation.FiledAliasName;
import com.lc.data.mybatis.query.in.InHelper;
import com.lc.data.mybatis.query.in.InValue;
import com.lc.data.mybatis.query.json.JsonHelper;
import com.lc.data.mybatis.query.json.JsonValue;
import com.lc.data.mybatis.query.range.RangeHelper;
import com.lc.data.mybatis.query.range.RangeValue;
import com.lc.data.mybatis.query.sort.SortHelper;
import com.lc.data.mybatis.query.sort.SortValue;
import com.lc.encryption.Aes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * QueryWrapper工具类
 *
 * @author liucheng
 * @since 2019-12-02
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QueryWrapperHelper {

    private static final String ALIAS_SPLIT = ".";

    /**
     * 保存当前线程获取到的实体的字段
     */
    private static ThreadLocal<Field[]> threadLocal = new ThreadLocal<>();

    /**
     * 获取QueryWrapper
     *
     * @param t   实体
     * @param <T> 泛型
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> getQueryWrapper(T t) {
        Field[] fields = ReflectUtil.getFields(t.getClass());
        setFields(fields);
        return new QueryWrapper<>(t);
    }

    /**
     * 组装安全数据对象
     *
     * @param queryWrapper   QueryWrapper
     * @param securityValues 安全对象值
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapSecurityQueryWrapper(QueryWrapper<T> queryWrapper, List<String> securityValues) {
        if (Objects.isNull(securityValues) || securityValues.isEmpty()) {
            return queryWrapper;
        }

        for (String securityValue : securityValues) {
            String[] securityValueArray = securityValue.split(",");
            if (securityValueArray.length != 2) {
                return queryWrapper;
            }

            queryWrapper.eq(StrUtil.toUnderlineCase(securityValueArray[0]), Aes.getInstance().encrypt(securityValueArray[1]));
        }

        return queryWrapper;
    }

    /**
     * 组装范围对象
     *
     * @param queryWrapper QueryWrapper
     * @param rangeValues  范围对象值
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapRangeQueryWrapper(QueryWrapper<T> queryWrapper, List<String> rangeValues) {
        if (Objects.isNull(rangeValues) || rangeValues.isEmpty()) {
            return queryWrapper;
        }

        for (String range : rangeValues) {
            RangeValue<Comparable> rangeValue = RangeHelper.getRangeValue(range);
            if (Objects.isNull(rangeValue.getLowerBound()) && Objects.isNull(rangeValue.getUpperBound())) {
                continue;
            }

            if (Objects.isNull(rangeValue.getUpperBound())) {
                queryWrapper.ge(StrUtil.toUnderlineCase(rangeValue.getField()), rangeValue.getLowerBound());
            } else if (Objects.isNull(rangeValue.getLowerBound())) {
                queryWrapper.le(StrUtil.toUnderlineCase(rangeValue.getField()), rangeValue.getUpperBound());
            } else {
                queryWrapper.between(StrUtil.toUnderlineCase(rangeValue.getField()),
                        rangeValue.getLowerBound(), rangeValue.getUpperBound());
            }
        }

        return queryWrapper;
    }

    /**
     * 组装集合对象
     *
     * @param queryWrapper QueryWrapper
     * @param inValues     集合值
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapInQueryWrapper(QueryWrapper<T> queryWrapper, List<String> inValues) {
        if (Objects.isNull(inValues) || inValues.isEmpty()) {
            return queryWrapper;
        }

        for (String in : inValues) {
            InValue<Comparable> inValue = InHelper.getInValue(in);
            if (inValue.isNegated()) {
                queryWrapper.notIn(StrUtil.toUnderlineCase(inValue.getField()), inValue.getVal());
            } else {
                queryWrapper.in(StrUtil.toUnderlineCase(inValue.getField()), inValue.getVal());
            }
        }

        return queryWrapper;
    }

    /**
     * 组装Json对象
     *
     * @param queryWrapper QueryWrapper
     * @param jsonValues   Json值
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapJsonQueryWrapper(QueryWrapper<T> queryWrapper, List<String> jsonValues) {
        if (Objects.isNull(jsonValues) || jsonValues.isEmpty()) {
            return queryWrapper;
        }

        for (String json : jsonValues) {
            JsonValue<Comparable> jsonValue = JsonHelper.getJsonValue(json);
            if (jsonValue.isArray()) {
                StringBuilder sql = new StringBuilder("JSON_CONTAINS(");
                sql.append(StrUtil.toUnderlineCase(jsonValue.getField()));
                if (StrUtil.isNotBlank(jsonValue.getPath())) {
                    sql.append("->'").append(jsonValue.getPath()).append("'");
                }
                sql.append(",JSON_ARRAY(");
                int size = jsonValue.getVal().size();
                for (int i = 0; i < size; i++) {
                    sql.append("{").append(i).append("}");
                    if (i < size - 1) {
                        sql.append(",");
                    }
                }
                sql.append("))");
                queryWrapper.apply(sql.toString(), jsonValue.getVal().toArray());
            } else {
                String val = jsonValue.getVal().get(0).toString();
                if (Objects.equals(jsonValue.getValueTypeEnum(), ValueTypeEnum.STRING)) {
                    val = "\"" + val + "\"";
                }
                if (StrUtil.isBlank(jsonValue.getPath())) {
                    queryWrapper.apply("JSON_CONTAINS(" + jsonValue.getField() + ",'" +
                            val + "')");
                } else {
                    queryWrapper.apply("JSON_CONTAINS(" + jsonValue.getField() + ", '" +
                            val + "','" + jsonValue.getPath() + "')");
                }
            }
        }

        return queryWrapper;
    }

    /**
     * 组装排序对象
     *
     * @param queryWrapper QueryWrapper
     * @param sortValues   排序值
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapSortQueryWrapper(QueryWrapper<T> queryWrapper, List<String> sortValues) {
        if (Objects.isNull(sortValues) || sortValues.isEmpty()) {
            return queryWrapper;
        }

        for (String sort : sortValues) {
            SortValue sortValue = SortHelper.getSort(sort);
            boolean isAsc = Objects.equals(SqlKeyword.ASC.getSqlSegment(), sortValue.getDirection());
            queryWrapper.orderBy(true, isAsc, StrUtil.toUnderlineCase(sortValue.getFiled()));
        }

        return queryWrapper;
    }

    /**
     *  获取排序sql
     * @param sortValues 排序值
     * @return java.lang.String 排序sql
     */
    public static String wrapSortQueryString(List<String> sortValues) {
        if (Objects.isNull(sortValues) || sortValues.isEmpty()) {
            return "";
        }

        StringBuffer sortSql = new StringBuffer(" order by ");
        int i = 0;
        for (String sort : sortValues) {
            SortValue sortValue = SortHelper.getSort(sort);
            boolean isAsc = Objects.equals(SqlKeyword.ASC.getSqlSegment(), sortValue.getDirection());
            if(isAsc) {
                sortSql.append(sortValue.getFiled()).append(" asc ").append(",");
            } else {
                sortSql.append(sortValue.getFiled()).append(" desc ").append(",");
            }
            i++;
        }
        if(i > 0) {
            return sortSql.substring(0,sortSql.length()-1);
        } else {
            return "";
        }
    }

    private static final String SERIAL_VERSION_UID_FILED = "serialVersionUID";
    private static final String LOG = "log";

    /**
     * 组装别名对象
     * @param queryWrapper
     * @param alias 别名
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapAliasQueryWrapper(QueryWrapper<T> queryWrapper, String alias) {
        alias = Objects.isNull(alias)?"":alias;
        T entity = queryWrapper.getEntity();
        if(Objects.isNull(entity)) {
            return queryWrapper;
        }
        Field[] fields = ReflectUtil.getFields(entity.getClass());
        StringBuffer querySql = new StringBuffer();

        for(Field field : fields) {
            Object fieldValue = ReflectUtil.getFieldValue(entity, field);

            if(Objects.nonNull(fieldValue) && !SERIAL_VERSION_UID_FILED.equalsIgnoreCase(field.getName()) &&
                    !LOG.equalsIgnoreCase(field.getName())) {
                handelQuerySql(alias,field,fieldValue,querySql);
            }
        }
        if(StringUtils.isNotBlank(querySql)) {
            queryWrapper.apply(querySql.substring(0, querySql.length() - 4));
            queryWrapper.setEntity(null);
        }
        return queryWrapper;
    }

    /**
     * 处理别名
     * @param realAlias 别名前缀
     * @param field 字段
     * @param fieldValue 字段值
     * @param querySql 查询sql
     */
    private static void handelQuerySql(String realAlias, Field field, Object fieldValue, StringBuffer querySql) {
        String tableFiledName = field.getName();
        String condition = "";
        TableField annotation = field.getAnnotation(TableField.class);
        if(Objects.nonNull(annotation)) {
            tableFiledName = StringUtils.isNotBlank(annotation.value())?annotation.value():tableFiledName;
            condition = StringUtils.isNotBlank(annotation.condition())?annotation.condition():condition;
        }
        FiledAliasName filedAliasName = field.getAnnotation(FiledAliasName.class);
        if(Objects.nonNull(filedAliasName)) {
            realAlias = "";
            tableFiledName = StringUtils.isNotBlank(filedAliasName.value())?filedAliasName.value():tableFiledName;
            condition = StringUtils.isNotBlank(filedAliasName.condition())?filedAliasName.condition():condition;
        }
        if(StringUtils.isNotBlank(condition)) {
            switch (condition) {
                case SqlCondition.EQUAL: querySql.append(realAlias).append(tableFiledName).append(" = '").append(fieldValue).append("'").append(" and "); break;
                case SqlCondition.NOT_EQUAL: querySql.append(realAlias).append(tableFiledName).append(" <> '").append(fieldValue).append("'").append(" and "); break;
                case SqlCondition.LIKE: querySql.append(realAlias).append(tableFiledName).append(" like '%").append(fieldValue).append("%'").append(" and "); break;
                case SqlCondition.LIKE_LEFT: querySql.append(realAlias).append(tableFiledName).append(" like '%").append(fieldValue).append("'").append(" and "); break;
                case SqlCondition.LIKE_RIGHT: querySql.append(realAlias).append(tableFiledName).append(" like '").append(fieldValue).append("%'").append(" and "); break;
                default:break;
            }
        } else {
            querySql.append(realAlias).append(tableFiledName).append("='").append(fieldValue).append("'").append(" and ");
        }
    }

    /**
     * 组装自定义参数sql
     *
     * @param queryWrapper QueryWrapper
     * @param applyValues   自定义sql
     * @return QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> wrapApplyQueryWrapper(QueryWrapper<T> queryWrapper, List<String> applyValues) {
        if (Objects.isNull(applyValues) || applyValues.isEmpty()) {
            return queryWrapper;
        }

        for (String apply : applyValues) {
            queryWrapper.apply(apply);
        }

        return queryWrapper;
    }

    /**
     * 获取当前线程的查询字段
     *
     * @return Field[]
     */
    public static Field[] getFields() {
        return threadLocal.get();
    }

    /**
     * 设置当前线程的查询字段
     *
     * @param fields Field[]
     */
    public static void setFields(Field[] fields) {
        threadLocal.set(fields);
    }

    /**
     * 移除当前线程的查询字段
     */
    public static void removeFields() {
        threadLocal.remove();
    }

    /**
     * 是否包含字段
     *
     * @param name 字段名称
     * @return boolean
     */
    public static boolean hasField(String name) {
        return hasField(threadLocal.get(), name);
    }

    /**
     * 是否包含字段
     *
     * @param fields 字段数组
     * @param name   字段名称
     * @return boolean
     */
    public static boolean hasField(final Field[] fields, String name) {
        if(name.contains(ALIAS_SPLIT)) {
            name = name.split(ALIAS_SPLIT)[1];
        }
        if (ArrayUtil.isNotEmpty(fields)) {
            for (Field field : fields) {
                if (name.equals(field.getName())) {
                    return true;
                }
                FiledAliasName fieldAnnotation = field.getAnnotation(FiledAliasName.class);
                if(Objects.nonNull(fieldAnnotation)) {
                    String aliasName = fieldAnnotation.value();
                    if(name.equalsIgnoreCase(aliasName.substring(aliasName.indexOf(".")+1,aliasName.length()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
