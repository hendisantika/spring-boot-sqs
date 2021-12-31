package com.hendisantika.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.annotation.PostConstruct;

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
    private AmazonSqsClient amazonSQSClient;
    private SQSConnectionFactory connectionFactory;

    @PostConstruct
    public void init() {
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        connectionFactory =
                new SQSConnectionFactory(providerConfiguration, amazonSQSClient.getClient());
    }
}
