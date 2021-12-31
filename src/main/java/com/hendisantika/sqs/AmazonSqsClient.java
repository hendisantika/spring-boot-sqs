package com.hendisantika.sqs;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-sqs
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 01/01/22
 * Time: 06.09
 */
@Service
public class AmazonSqsClient {

    private AmazonSQS client;

    @Value("${aws.access.key}")
    private String ACCESS_KEY;

    @Value("${aws.secret.key}")
    private String SECRET_KEY;

    @PostConstruct
    private void initializeAmazonSqsClient() {
        this.client =
                AmazonSQSClientBuilder.standard()
                        .withCredentials(getAwsCredentialProvider())
                        .withRegion(Region.getRegion(Regions.AP_SOUTHEAST_1).getName())
                        .build();
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    public AmazonSQS getClient() {
        return client;
    }
}
