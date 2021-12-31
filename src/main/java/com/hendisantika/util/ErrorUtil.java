package com.hendisantika.util;

import com.hendisantika.model.ErrorModel;
import com.hendisantika.model.ServiceResponse;
import com.hendisantika.model.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.05
 */
public class ErrorUtil {
    public static ServiceResponse requestErrorHandler(BindingResult bindingResult) {
        ServiceResponse response = new ServiceResponse();
        List<ErrorModel> errorModelList = new ArrayList();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setField(fieldError.getField());
            errorModel.setMessage(fieldError.getDefaultMessage());
            errorModel.setDescription(fieldError.getCode());
            errorModelList.add(errorModel);
        }

        response.setErrorList(errorModelList);
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setStatusCode(StatusCode.ERROR);
        return response;
    }

    public static List<ErrorModel> prepareInternalServerErrorResponse() {
        ErrorModel errorModel = new ErrorModel("Internal server Error", "", "Internal server Error");
        List<ErrorModel> errorList = new ArrayList<>();
        errorList.add(errorModel);
        return errorList;
    }
}
