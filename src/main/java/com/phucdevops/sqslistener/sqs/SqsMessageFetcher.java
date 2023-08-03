package com.phucdevops.sqslistener.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.phucdevops.sqslistener.constant.AppProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SqsMessageFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsMessageFetcher.class);

    private final AmazonSQS sqsClient;
    private final AppProperties appProperties;

    List<Message> fetchMessage() {

        ReceiveMessageRequest request = new ReceiveMessageRequest()
                .withMaxNumberOfMessages(10) // pro
                .withQueueUrl(appProperties.sqsTopicUrl)
                .withWaitTimeSeconds(1);

        ReceiveMessageResult result = sqsClient.receiveMessage(request);
        if (result.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            LOGGER.error("Get error response from SQS queue {}: {}",
                    appProperties.sqsTopicUrl, result.getSdkHttpMetadata());
            return Collections.emptyList();
        }

        LOGGER.debug("Polled {} messages from SQS queue {}",
                result.getMessages().size(), appProperties.sqsTopicUrl);

        return result.getMessages();
    }
}
