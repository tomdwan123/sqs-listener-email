package com.phucdevops.sqslistener.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.config.aws.message.queue.topic.url}")
    public String sqsTopicUrl;

    @Value("${${app.config.aws.message.queue.max_number_messages_polling}")
    public int sqsMaxNumberMessagesPolling;

    @Value("${${app.config.aws.message.queue.wait_time_second_polling}")
    public int sqsWaitTimeSecondsPolling;
}