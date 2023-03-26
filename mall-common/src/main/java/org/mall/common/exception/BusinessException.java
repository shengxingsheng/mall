package org.mall.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.mall.common.constant.ErrorCode;

/**
 * @author sxs
 * @since 2023/1/17
 */
@Getter
@Setter
public class BusinessException extends RuntimeException{
    private ErrorCode errorCode;
    private String msg;
    public BusinessException(ErrorCode errorCode) {

        this.errorCode = errorCode;
    }
    public BusinessException(ErrorCode errorCode,String msg) {
        this.msg=msg;
        this.errorCode = errorCode;
    }


    @Override
    public String toString() {
        return super.toString()+"BusinessException{" +
                "errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
