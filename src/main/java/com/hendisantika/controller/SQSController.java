package com.hendisantika.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;

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

    @GetMapping("/createQueue")
    public void createQueue() {
        String queueName = QUEUE_PREFIX + System.currentTimeMillis();

        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(queueName)
                .build();

        SQS_CLIENT.createQueue(createQueueRequest);

        GetQueueUrlResponse getQueueUrlResponse =
                SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
        queueUrl = getQueueUrlResponse.queueUrl();
    }

    @GetMapping("listQueues")
    public String listQueues() {
        ListQueuesRequest listQueuesRequest = ListQueuesRequest.builder()
                .queueNamePrefix(QUEUE_PREFIX)
                .build();
        ListQueuesResponse listQueuesResponse = SQS_CLIENT.listQueues(listQueuesRequest);

        String queues = "";
        for (String url : listQueuesResponse.queueUrls()) {
            queues += url + "\n";
        }

        return queues;
    }
}
