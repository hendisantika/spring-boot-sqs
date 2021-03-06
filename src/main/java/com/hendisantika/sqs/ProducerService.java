package com.hendisantika.sqs;

import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.17
 */
@Service
public class ProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerService.class);
    @Autowired
    AmazonSqsClient amazonSQSClient;
    @Autowired
    private JmsTemplate defaultJmsTemplate;

    public void send(String queueName, String requestBody) {
        LOG.info("Send to {} message --> {}", queueName, requestBody);
        defaultJmsTemplate.convertAndSend(queueName, requestBody);
    }

    public void sendMultiple() {
        SendMessageBatchRequestEntry[] dataArray = new SendMessageBatchRequestEntry[10];
        for (int i = 0; i < 10; i++) {
            dataArray[i] =
                    new SendMessageBatchRequestEntry(UUID.randomUUID().toString(), "Hello from message " + i);
        }

        LOG.info("Sending batch message to order queue");

        SendMessageBatchRequest sendBatchRequest =
                new SendMessageBatchRequest()
                        .withQueueUrl("queue-url")
                        .withEntries(dataArray);
        amazonSQSClient.getClient().sendMessageBatch(sendBatchRequest);
    }
}
