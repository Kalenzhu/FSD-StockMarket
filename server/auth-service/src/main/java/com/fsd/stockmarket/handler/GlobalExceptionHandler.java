package com.fsd.stockmarket.handler;

import java.util.Objects;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fsd.stockmarket.exception.ServiceErrorCode;
import com.fsd.stockmarket.exception.ServiceException;
import com.fsd.stockmarket.pojo.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result<String> handleServiceException(ServiceException ex){
		log.error("occurs ServiceException, code = '"+ex.getErrorCode().intValue()+"', errorMsg = '"+ex.getErrorMessage()+"'", ex);
        return new Result<String>(ex.getErrorMessage(), "failed", ex.getErrorCode().intValue());
    }
	
	@ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidException(MethodArgumentNotValidException e){
        log.error(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new Result<String>(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), ServiceErrorCode.BAD_REQUEST.value());
    }
	
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<String> handleRuntimeException(RuntimeException ex){
		log.error("occurs RuntimeException: ", ex);
        return new Result<String>("runtime error", "failed", ServiceErrorCode.INTERNAL_ERROR.value());
    }
}
