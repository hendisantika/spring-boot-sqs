package com.hendisantika.controller;

import com.hendisantika.model.ServiceResponse;
import com.hendisantika.model.StatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.01
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        ServiceResponse serviceResponse =
                new ServiceResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        StatusCode.ERROR,
                        null,
                        ErrorUtil.prepareInternalServerErrorResponse());
        return handleExceptionInternal(
                ex, serviceResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        ServiceResponse serviceResponse =
                new ServiceResponse(
                        HttpStatus.BAD_REQUEST,
                        StatusCode.ERROR,
                        null,
                        ErrorUtil.prepareErrorResponse(ex.getMessage()));
        return handleExceptionInternal(
                ex, serviceResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
