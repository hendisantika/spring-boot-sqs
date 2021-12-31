package com.hendisantika.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> implements Serializable {

    private HttpStatus status; // OK_Failure
    private StatusCode statusCode; // code
    private T body;
    private List<T> errorList;

}
