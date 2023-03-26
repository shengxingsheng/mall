package org.mall.common.constant;

import lombok.ToString;

/**
 * @author sxs
 * @since 2023/1/13
 */
@ToString
public enum ErrorCode {
    OK("00000", "一切ok"),
    //1.用户端错误,一级宏观错误码
    USER_ERROR("A0001", "用户端错误 "),
    //1.1 用户注册错误 ,二级宏观错误码
    USER_REGISTER_ERROR("A0100", "用户注册错误"),
    USER_NAME_REPEAT("A0111", "用户名重复"),
    USER_PHONE_REPEAT("A0114", "手机号已存在"),
    USER_CODE_ERROR("A0131", "短信校验码错误,请重新获取"),
    USER_PHONE_ERROR("A0151", "手机格式校验失败"),
    USER_SEND_CODE_BUSY("A0154", "验证码请求频繁"),
    //1.2 用户登录异常,二级宏观错误码
    USER_LOGIN_ERROR("A0200", "A0200"),
    USER_ACCOUNT_ERROR("A0201", "用户账号不存在"),
    USER_PASSWORD_ERROR("A0210", "用户密码错误"),
    USER_REQUEST_PARAMETER_ERROR("A0400", "用户请求参数错误"),
    USER_REQUEST_PARAMETER_IS_NULL("A0410", "请求必填参数为空"),
    USER_REQUEST_PARAMETER_MISMATCH("A0421", "请求参数格式不匹配"),
    QUANTITY_EXCEEDS_LIMIT("A0425", "数量超出限制"),
    USER_REQUEST_JSON_RESOLVE_FAIL("A0427", "请求 JSON 解析失败"),
    ORDER_CLOSED("A0443", "订单已关闭"),
    USER_RESOURCE_EXCEPTION("A0600", "用户资源异常"),
    /**
     * 系统执行出错
     */
    SYSTEM_EXECUTE_ERROR("B0001", "系统执行出错"),
    SYSTEM_DISASTER_RECOVERY_TRIGGERED("B0200", "系统容灾功能被触发"),
    SYSTEM_CURRENT_LIMITING("B0201", "系统限流"),
    SYSTEM_RESOURCE_EXCEPTION("B0300", "系统资源异常"),

    /**
     *
     */
    THIRD_PART_SERVICE_ERROR("C0001", "调用第三方服务出错"),
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),
    FEIGN_SERVICE_ERROR("C0110", "Feign服务出错"),
    RPC_SERVICE_ERROR("C0110", "RPC服务出错"),
    SERVICE_TIME_OUT("C0200", "第三方系统执行超时"),
    ORDER_SERVICE_TIME_OUT("C0211", "调用Order服务超时"),
    DATABASE_SERVICE_ERROR("C0300", "数据库服务出错"),
    DATABASE_SERVICE_TIME_OUT("C0400", "第三方容灾系统被触发"),
    NOTICE_SERVICE_ERROR("C0500", "通知服务出错");

    String code;
    String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public boolean equals(ErrorCode errorCode){
        if (this.code.equals(errorCode.getCode())) {
            return true;
        }
        return false;
    }
}
