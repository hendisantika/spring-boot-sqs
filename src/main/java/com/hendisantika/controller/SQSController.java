package com.hendisantika.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 05.47
 */
@RestController
@RequestMapping("/sqs")
public class SQSController {
    private static final String QUEUE_PREFIX = "MyAWSPlanetSQS-";
    private static final SqsClient SQS_CLIENT = SqsClient.builder().region(Region.AP_SOUTHEAST_1).build();
    private static String queueUrl;
}
