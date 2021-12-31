package com.hendisantika.util;

import com.hendisantika.model.ServiceResponse;
import com.hendisantika.model.StatusCode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.07
 */
public class Util {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <U, V> V convertClass(U mapperObject, Class<V> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mapperObject, targetClass);
    }

    public static <Source, Dest> void copyProperty(Source source, Dest target) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

    public static <U, V> List<V> toDtoList(List<U> mapperObjects, Class<V> targetClass) {
        List<V> dtoObjects =
                mapperObjects.stream().map(u -> convertClass(u, targetClass)).collect(Collectors.toList());

        return dtoObjects;
    }

    public static ServiceResponse prepareSuccessResponse(Object data) {
        ServiceResponse response = new ServiceResponse();
        response.setBody(data);
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(StatusCode.SUCCESS);
        return response;
    }
}
