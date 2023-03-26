package org.mall.common.util;

import org.mall.common.constant.ErrorCode;
import org.mall.common.pojo.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/1/13
 */

public class ErrorCodeUtil {
    private static Map<String, ErrorCode> map = new HashMap<>();

    static {
        ErrorCode[] errorCodes = ErrorCode.values();
        Arrays.stream(errorCodes).forEach(errorCode -> {
            map.put(errorCode.getCode(),errorCode);
        });
    }

    public static ErrorCode getErrorCode(String code) {
        return map.get(code);
    }
    public static ErrorCode getErrorCode(ResponseEntity responseEntity) {
        return map.get(responseEntity.getCode());
    }
}
