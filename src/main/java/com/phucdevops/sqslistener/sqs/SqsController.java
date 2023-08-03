package com.phucdevops.sqslistener.sqs;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sqs")
public class SqsController {

    private final SqsService sqsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SqsController.class);

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/send")
    public ResponseEntity<?> sendMessage() {

        sqsService.sendMessage();
        return new ResponseEntity<>("Send message OK!", HttpStatus.OK);
    }

    @GetMapping("/receive")
    public ResponseEntity<?> receiveMessage() {

        sqsService.receiveMessage();
        return new ResponseEntity<>("Receive message OK!", HttpStatus.OK);
    }
}
