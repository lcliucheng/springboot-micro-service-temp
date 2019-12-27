package com.lc.data.mybatis.utils;


import com.google.common.collect.Lists;
import com.lc.data.mybatis.query.QueryParamRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


/**
 * <p> ADMIN工具类 </p>
 *
 * @author liucheng
 * @since 2019-12-18
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParamUtil {

    public static MultiValueMap<String, String> dealQueryParam(QueryParamRequest param) {
        MultiValueMap<String, String> multiValueParams = new LinkedMultiValueMap<> ();
        String json = param.getJson();
        if (StringUtils.isNotBlank(json)) {
            if (json.contains(";")) {
                multiValueParams.put("json", Lists.newArrayList(json.split(";")));
            } else {
                multiValueParams.put("json", Lists.newArrayList(json));
            }
        }
        String sort = param.getSort();
        if (StringUtils.isNotBlank(sort)) {
            if (sort.contains(";")) {
                multiValueParams.put("sort", Lists.newArrayList(sort.split(";")));
            } else {
                multiValueParams.put("sort", Lists.newArrayList(sort));
            }
        }
        String range = param.getRange();
        if (StringUtils.isNotBlank(range)) {
            if (range.contains(";")) {
                multiValueParams.put("range", Lists.newArrayList(range.split(";")));
            } else {
                multiValueParams.put("range", Lists.newArrayList(range));
            }
        }
        String security = param.getSecurity();
        if (StringUtils.isNotBlank(security)) {
            if (security.contains(";")) {
                multiValueParams.put("security", Lists.newArrayList(security.split(";")));
            } else {
                multiValueParams.put("security", Lists.newArrayList(security));
            }
        }
        String in = param.getIn();
        if (StringUtils.isNotBlank(in)) {
            if (in.contains(";")) {
                multiValueParams.put("in", Lists.newArrayList(in.split(";")));
            } else {
                multiValueParams.put("in", Lists.newArrayList(in));
            }
        }
        return multiValueParams;
    }


}
