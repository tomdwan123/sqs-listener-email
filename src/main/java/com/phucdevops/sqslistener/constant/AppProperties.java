package com.phucdevops.sqslistener.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.config.aws.message.queue.topic.url}")
    public String sqsTopicUrl;
}