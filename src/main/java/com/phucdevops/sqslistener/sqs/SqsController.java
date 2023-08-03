package com.phucdevops.sqslistener.sqs;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sqs")
public class SqsController {

    private final SqsService sqsService;

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
