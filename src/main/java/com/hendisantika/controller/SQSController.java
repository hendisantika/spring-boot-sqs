package com.hendisantika.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;

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
    private static final String DLQ_QUEUE_NAME = "MyAWSPlanetSQS-DLQ";
    private static String dlqQueueUrl;

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

    @PostMapping("sendMessage")
    public void sendMessage(@RequestParam("text") String text) {
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(text)
                .build();
        SQS_CLIENT.sendMessage(messageRequest);
    }

    @GetMapping("receiveMessagesWithoutDelete")
    public String receiveMessagesWithoutDelete() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .build();
        List<Message> receivedMessages = SQS_CLIENT.receiveMessage(receiveMessageRequest).messages();

        String messages = "";
        for (Message receivedMessage : receivedMessages) {
            messages += receivedMessage.body() + "\n";
        }
        return messages;
    }

    @GetMapping("receiveMessagesWithDelete")
    public String receiveMessagesWithDelete() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .build();
        List<Message> receivedMessages = SQS_CLIENT.receiveMessage(receiveMessageRequest).messages();

        String messages = "";
        for (Message receivedMessage : receivedMessages) {
            messages += receivedMessage.body() + "\n";
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(receivedMessage.receiptHandle())
                    .build();
            SQS_CLIENT.deleteMessage(deleteMessageRequest);
        }
        return messages;
    }

    @GetMapping("createDLQ")
    public void createDLQ() {
        // Create the DLQ
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(DLQ_QUEUE_NAME)
                .build();

        SQS_CLIENT.createQueue(createQueueRequest);

        GetQueueUrlResponse getQueueUrlResponse =
                SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder()
                        .queueName(DLQ_QUEUE_NAME)
                        .build());
        dlqQueueUrl = getQueueUrlResponse.queueUrl();

        // Link the DLQ to the source queue
        GetQueueAttributesResponse queueAttributes = SQS_CLIENT.getQueueAttributes(GetQueueAttributesRequest.builder()
                .queueUrl(DLQ_QUEUE_NAME)
                .attributeNames(QueueAttributeName.QUEUE_ARN)
                .build());
        String dlqArn = queueAttributes.attributes().get(QueueAttributeName.QUEUE_ARN);

        // Specify the Redrive Policy
        HashMap<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        attributes.put(QueueAttributeName.REDRIVE_POLICY, "{\"maxReceiveCount\":\"3\", \"deadLetterTargetArn\":\""
                + dlqArn + "\"}");

        SetQueueAttributesRequest setAttrRequest = SetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributes(attributes)
                .build();

        SetQueueAttributesResponse setAttrResponse = SQS_CLIENT.setQueueAttributes(setAttrRequest);
    }

    @GetMapping("receiveMessagesWithoutDeleteLimitedVisibilityTimeout")
    public void receiveMessagesWithoutDeleteLimitedVisibilityTimeout() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).build();
        String receipt = SQS_CLIENT.receiveMessage(receiveMessageRequest)
                .messages()
                .get(0)
                .receiptHandle();

        ChangeMessageVisibilityRequest visibilityRequest = ChangeMessageVisibilityRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(receipt)
                .visibilityTimeout(5)
                .build();
        SQS_CLIENT.changeMessageVisibility(visibilityRequest);
    }

    @GetMapping("/createQueueWithLongPolling")
    public void createQueueWithLongPolling() {
        String queueName = QUEUE_PREFIX + System.currentTimeMillis();

        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName(queueName).build();

        SQS_CLIENT.createQueue(createQueueRequest);

        GetQueueUrlResponse getQueueUrlResponse =
                SQS_CLIENT.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
        queueUrl = getQueueUrlResponse.queueUrl();

        HashMap<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS, "20");

        SetQueueAttributesRequest setAttrsRequest = SetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributes(attributes)
                .build();

        SQS_CLIENT.setQueueAttributes(setAttrsRequest);
    }
}
