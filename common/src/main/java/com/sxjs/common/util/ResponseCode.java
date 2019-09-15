package com.sxjs.common.util;

/**
 * @Auther: xp
 * @Date: 2019/9/14 19:06
 * @Description:
 */
public class ResponseCode {

    /**
     * 200200	成功
     * 200400	请求头缺少参数{}
     * 200600	请求体缺少参数{}
     * 200800	请求参数值{}错误
     * 200998	自定义错误
     * 200999	Session失效
     */

    /**
     * request  成功
     */
    public static final String SUCCESS_OK = "200200";

    /**
     * 请求头缺少参数{}
     */
    public static final String RESPONSE_HEAHER_ERROR = "200400";
    /**
     * 请求体缺少参数{}
     */
    public static final String RESPONSE_BODY_ERROR   = "200600";

    /**
     * 请求参数值{}错误
     */
    public static final String REQUEST_PARAMETER_ERROR = "200800";

    /**
     * 自定义错误
     */
    public static final String CUSTOM_ERROR  = "200998";
    /**
     * Session失效
     */

    public static final String SEESION_ERROR = "200999";

}
