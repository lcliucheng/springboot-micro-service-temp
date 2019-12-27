package com.lc.admin.utils;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lc.admin.consts.UserType;
import com.lc.admin.domain.entity.User;
import com.lc.admin.security.SecurityUserDetails;
import com.lc.admin.service.IAdminUserService;
import com.lc.common.AuthContext;
import com.lc.data.mybatis.query.QueryParamRequest;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * <p> ADMIN工具类 </p>
 *
 * @author liucheng
 * @since 2019-12-18
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdminUtil {

    public static MultiValueMap<String, Object> dealQueryParam(QueryParamRequest param) {
        MultiValueMap<String, Object> multiValueParams = new LinkedMultiValueMap<>();
        Map<String, Object> paramMap = BeanUtil.beanToMap(param,false,true);
        paramMap.forEach((k, v) -> multiValueParams.put(k, Lists.newArrayList(v)));
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
        return multiValueParams;
    }

    public static MultiValueMap<String, String> dealQueryParamToMap(QueryParamRequest param) {
        MultiValueMap<String, String> multiValueParams = new LinkedMultiValueMap<>();
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

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile 手机号码
     * @return 手机号
     * @author liucheng
     */
    public static String mobileSecret(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 身份证前四后四脱敏
     *
     * @param id 身份证号码
     * @return 身份证号码
     * @author liucheng
     * @date 2019/5/20
     */
    public static String idNoSecret(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{4})\\w(?=\\w{4})", "*");
    }

    /**
     * 姓名脱敏
     *
     * @param fullName 姓名
     * @author liucheng
     * @date 2019/5/20
     */
    public static String nameSecret(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        if (fullName.length() == 2) {
            return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
        String rightName = StringUtils.right(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName) - 1, "*") + rightName;
    }

    /**
     * 查询用户类型
     *
     * @author liucheng
     * @param * @param null 1
     * @return
     * @date 2019/9/5
     */
    public static String quertUserType(IAdminUserService userService){
        SecurityUserDetails userDetails = (SecurityUserDetails) AuthContext.getJwtToken().getUserDetails();
        String type = userDetails.getUser().getType();
        if(Objects.equals(UserType.CHANNEL.getValue(),type)){
            return userDetails.getUsername();
        }else if(Objects.equals(UserType.BUSI.getValue(),type)){
            QueryWrapper<User> wrapper = new QueryWrapper<>(new User().setBusiUserName(userDetails.getUsername()));
            List<User> list = userService.list(wrapper);
            if(list != null && !list.isEmpty()){
                List<String> panChannelList = Lists.newArrayList();
                list.forEach(item -> panChannelList.add(item.getUsername()));
                return "panChannel,STRING," + org.apache.commons.lang3.StringUtils.join(panChannelList, ",");
            }
        }
        return null;
    }

}
