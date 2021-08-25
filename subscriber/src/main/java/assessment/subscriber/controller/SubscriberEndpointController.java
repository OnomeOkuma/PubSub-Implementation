package assessment.subscriber.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriberEndpointController {

    private Logger logger = LoggerFactory.getLogger(SubscriberEndpointController.class);

    @PostMapping("/test1")
    public ResponseEntity<Object> endpoint1(@RequestBody String payload) {
        logger.info("Publisher Payload endpoint 1: {}", payload);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/test2")
    public ResponseEntity<Object> endpoint2(@RequestBody String payload) {
        logger.info("Publisher Payload endpoint 2: {}", payload);

        return ResponseEntity.ok("Success");
    }
}
