package org.mall.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.constant.ErrorCode;

import java.io.Serializable;

/**
 * @author sxs
 * @since 2023/1/13
 */
@Getter
@Setter
@ToString
@Schema(description = "统一响应实体")
public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "错误码",requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
    @Schema(description = "后端出错原因")
    private String msg;
    @Schema(description = "数据")
    private T data;

    public ResponseEntity() {
    }

    private ResponseEntity(ErrorCode errorCodeEnum, T data) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
        this.data = data;
    }
    private ResponseEntity(ErrorCode errorCodeEnum) {
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }



    /**
     * 成功返回
     * @param data
     * @return
     * @param <T>
     */
    public static <T>ResponseEntity ok(T data) {
        return new ResponseEntity<T>(ErrorCode.OK,data);
    }
    public static ResponseEntity ok() {
        return new ResponseEntity(ErrorCode.OK);
    }
    /**
     *
     * @param errorCodeEnum
     * @return
     */
    public static ResponseEntity error(ErrorCode errorCodeEnum) {
        return new ResponseEntity(errorCodeEnum);
    }
    public static ResponseEntity error(ErrorCode errorCodeEnum,Object data) {
        return new ResponseEntity(errorCodeEnum,data);
    }
    @JsonIgnore
    public boolean isOK() {
        return this.code.equals(ErrorCode.OK.getCode());
    }
    public boolean nonOK() {
        return !isOK();
    }
}
