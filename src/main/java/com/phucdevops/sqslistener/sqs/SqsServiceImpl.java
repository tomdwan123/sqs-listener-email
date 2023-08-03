package com.phucdevops.sqslistener.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.phucdevops.sqslistener.constant.AppProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SqsServiceImpl implements SqsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsServiceImpl.class);
    private final AmazonSQS sqsClient;
    private final AppProperties appProperties;
    private final SqsMessagePoller messagePoller;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void sendMessage() {

        LOGGER.info("------------ START SEND MESSAGE ------------");
        for (int i = 1; i <= 50; i++) {
            String message = String.format("fake%d@gmail.com", i);
            Message<String> payload = MessageBuilder.withPayload(message).build();
            queueMessagingTemplate.send(appProperties.sqsTopicUrl, payload);
        }
    }

    @Override
    public void receiveMessage() {
        LOGGER.info("------------ START RECEIVE MESSAGE ------------");
        messagePoller.start();
    }
}
