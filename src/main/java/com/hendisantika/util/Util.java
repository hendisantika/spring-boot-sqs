package com.hendisantika.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
}
