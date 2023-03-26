package org.mall.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.ErrorCode;
import org.mall.common.pojo.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/13
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleHttpMessageException(BindException e) {
        log.error(e.toString());
        List<FieldError> allErrors = e.getBindingResult().getFieldErrors();
//        Map<String,String> map = new HashMap<>();
        for (FieldError error : allErrors) {
            log.error("{}:{}",error.getField(),error.getDefaultMessage());
//            map.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.error(ErrorCode.USER_REQUEST_PARAMETER_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
        log.error(e.toString());
        return ResponseEntity.error(ErrorCode.USER_REQUEST_PARAMETER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleValidationException(HttpMessageNotReadableException e) {
        log.error(e.toString());
        return ResponseEntity.error(ErrorCode.USER_REQUEST_JSON_RESOLVE_FAIL);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentTypeMismatchException e) {
        log.error(e.toString());
        return ResponseEntity.error(ErrorCode.USER_REQUEST_PARAMETER_MISMATCH);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException e) {
        log.error(e.toString());
        return ResponseEntity.error(e.getErrorCode());
    }
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable exception) {
        log.error(exception.toString());
        ResponseEntity<Object> responseEntity = new ResponseEntity<>();
        responseEntity.setCode(ErrorCode.SYSTEM_EXECUTE_ERROR.getCode());
        responseEntity.setMsg("系统繁忙,请稍后重试");
        return responseEntity;
    }
}
