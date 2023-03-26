package org.mall.common.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.Cookie;

/**
 * @author sxs
 * @since 2023/2/16
 */
public class CookieUtil {
    public static String getCookieValue(Cookie[] cookies,String key) {
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
