package org.mall.common.constant;

/**
 * @author sxs
 * @since 2023/2/15
 */
public class CommonConstant {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * springSession设置登录用户信息的key
     */
    public static final String LOGIN_SESSION_KEY = "loginUser";

    /**
     * cookie的key
     */
    public static final String TEMP_USER_COOKIE_NAME = "user-key";
    /**
     * cookie存活时间 一个月
     */
    public static final int TEMP_USER_COOKIE_SURVIVE = 60*60*24*30;


}
