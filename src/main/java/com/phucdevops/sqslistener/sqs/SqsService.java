package com.phucdevops.sqslistener.sqs;

public interface SqsService {

    void sendMessage();

    void receiveMessage();
}
