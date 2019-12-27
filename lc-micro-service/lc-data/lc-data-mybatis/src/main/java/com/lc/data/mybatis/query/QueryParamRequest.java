package com.lc.data.mybatis.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 活动
 *
 * @author liucheng
 * @date 2019/05/18
 */

@ApiModel(value = "QueryParamRequest", description = "公共请求参数")
@Data
@Accessors(chain = true)
public class QueryParamRequest {

    private Integer page = 1;
    private Integer size = 10;
    private String sort = "createdAt,desc";
    private String range;
    private String json;
    private String security;
    private String in;

}
