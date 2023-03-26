package org.mall.common.util;

import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author sxs
 * @since 2023/1/17
 */
public class Assert {
    private Assert(){}
    public static void notNull(@Nullable Object object) {
        if (object == null) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR);
        }
    }
    public static void notNull(@Nullable Object object,String msg) {
        if (object == null) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR,msg);
        }
    }
    public static void notBlank(@Nullable String object,String msg) {
        if (StringUtils.isBlank(object)) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR,msg);
        }
    }
    public static void notNull(@Nullable Object object,ErrorCode errorCode,String msg) {
        if (object == null) {
            throw new BusinessException(errorCode,msg);
        }
    }
    public static void notBlank(@Nullable String object,ErrorCode errorCode,String msg) {
        if (StringUtils.isBlank(object)) {
            throw new BusinessException(errorCode,msg);
        }
    }
    public static void isBlank(@Nullable String object,ErrorCode errorCode,String msg) {
        if (StringUtils.isNotBlank(object)) {
            throw new BusinessException(errorCode,msg);
        }
    }
    /**
     * 集合不能为空
     * @param collection
     * @param msg
     */
    public static void notEmpty(@Nullable Collection collection, String msg) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR,msg);
        }
    }

    /**
     * flag 应为true
     * @param flag
     * @param msg
     */
    public static void isTrue(@Nullable Boolean flag, String msg) {
        if (!flag) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR,msg);
        }
    }

    public static void isOK(ResponseEntity response) {
        if (response.nonOK()) {
            throw new BusinessException(ErrorCodeUtil.getErrorCode(response));
        }
    }
}
