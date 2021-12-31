package com.hendisantika.sqs;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.15
 */
@Configuration
@EnableJms
public class JmsConfig {

    @Autowired
    AmazonSqsClient amazonSQSClient;
    private SQSConnectionFactory connectionFactory;
}
