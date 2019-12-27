package com.lc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lc.exception.ErrorCode;
import com.lc.exception.ErrorInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用返回对象，所有http接口都必须返回此对象
 *
 * @author liucheng
 * @since 2019-12-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Response", description = "响应数据")
public class Response<T> implements Serializable {

    public static final Integer SUCCESS_CODE = 200;
    private static final long serialVersionUID = 1L;
    /**
     * 响应代码
     */
    @ApiModelProperty("响应代码")
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty("响应消息")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    /**
     * md5值
     */
    @ApiModelProperty("md5值")
    private String md5;

    /**
     * 成功
     *
     * @param <T> 返回数据类型
     * @return DataResponse
     */
    public static <T> Response<T> success() {
        return new Response<>(SUCCESS_CODE, "操作成功", null,null);
    }

    /**
     * 成功
     *
     * @param data 消息
     * @param <T>  返回数据类型
     * @return DataResponse
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS_CODE, "操作成功", data,null);
    }

    /**
     * 成功
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  返回数据类型
     * @return Response
     */
    public static <T> Response<T> success(String msg, T data) {
        return new Response<>(SUCCESS_CODE, msg, data,null);
    }

    /**
     * 失败
     *
     * @param errorCode ErrorCode
     * @return Response
     */
    public static <T> Response<T> failed(ErrorCode errorCode) {
        return new Response<>(errorCode.getValue(), errorCode.getDesc(), null,null);
    }

    /**
     * 失败
     *
     * @param errorInfo 错误信息
     * @return Response
     */
    public static <T> Response<T> failed(ErrorInfo errorInfo) {
        return new Response<>(errorInfo.getCode(), errorInfo.getMsg(), null,null);
    }

    /**
     * 失败
     *
     * @param errorCode ErrorCode
     * @param <T>       返回数据类型
     * @return Response
     */
    public static <T> Response<T> failed(ErrorCode errorCode, T data) {
        return new Response<>(errorCode.getValue(), errorCode.getDesc(), data,null);
    }

    /**
     * 失败
     *
     * @param code 数据
     * @param msg  消息
     * @return Response
     */
    public static <T> Response<T> failed(int code, String msg) {
        return new Response<>(code, msg, null,null);
    }

    /**
     * 失败
     *
     * @param msg 消息
     * @return Response
     */
    public static <T> Response<T> failed(String msg) {
        return new Response<>(ErrorCode.SYSTEM_ERROR.getValue(), msg, null,null);
    }

    /**
     * 失败
     *
     * @param code 数据
     * @param msg  消息
     * @param <T>  返回数据类型
     * @return Response
     */
    public static <T> Response<T> failed(int code, String msg, T data) {
        return new Response<>(code, msg, data,null);
    }

    /**
     * 创建返回对象
     *
     * @param code 代码
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return Response
     */
    public static <T> Response<T> response(int code, String msg, T data) {
        return new Response<>(code, msg, data,null);
    }

    /**
     * 判断响应结果是否成功
     *
     * @return 结果
     */
    public boolean isSuccess() {
        if (Objects.isNull(code)) {
            return false;
        }

        return Objects.equals(SUCCESS_CODE, code);
    }

}
