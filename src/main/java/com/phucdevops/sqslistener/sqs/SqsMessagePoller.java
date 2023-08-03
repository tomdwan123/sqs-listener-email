package com.phucdevops.sqslistener.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.phucdevops.sqslistener.constant.AppProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SqsMessagePoller {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsMessagePoller.class);
    private final AmazonSQS sqsClient;
    private final AppProperties appProperties;
    private final SqsMessageFetcher messageFetcher;
    private final ThreadPoolExecutor handlerThreadPool
            = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    private final ScheduledThreadPoolExecutor pollerThreadPool
            = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);

    private void poll() {

        List<Message> messages = messageFetcher.fetchMessage();
        for (Message sqsMessage : messages) {
            String message = sqsMessage.getBody();
            handlerThreadPool.submit(() -> {
                // TODO send email
                acknowledgeMessage(sqsMessage);
                LOGGER.info("-> Poll message: {} - on {} (s)",
                        message, new Date());
            });
        }

    }

    private void acknowledgeMessage(Message message) {
        sqsClient.deleteMessage(appProperties.sqsTopicUrl, message.getReceiptHandle());
    }

    void start() {

        for (int i = 0; i < pollerThreadPool.getCorePoolSize(); i++) {
            LOGGER.info("Starting poller - at schedule thread {} - at {}", i, new Date());
            pollerThreadPool.scheduleWithFixedDelay(
                    this::poll,
                    1,
                    1,
                    TimeUnit.SECONDS
            );
            LOGGER.info("--------------------------------------------");
        }
    }

    void stop() {

        LOGGER.info("Stopping SqsMessagePoller");
        pollerThreadPool.shutdownNow();
        handlerThreadPool.shutdownNow();
    }
}
