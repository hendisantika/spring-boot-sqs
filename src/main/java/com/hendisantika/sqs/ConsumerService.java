package com.hendisantika.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.14
 */
@Service
public class ConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerService.class);

    @JmsListener(destination = "${queue.order}")
    public void process(String data) {
        LOG.info("Received message " + data);
    }
}
