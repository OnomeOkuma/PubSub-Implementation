package assessment.publisher.controllers;

import assessment.publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class PublisherController {

    private final PublisherService service;

    @Autowired
    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @PostMapping(value = "/{topic}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> publish (@PathVariable String topic, @RequestBody String payload) throws Exception {

        if(!topic.matches("([a-zA-Z])\\w+")) {
            return ResponseEntity.badRequest().body("Invalid Topic");
        }


        service.publishMessage(topic, payload);
        return ResponseEntity.ok("Success");
    }
}
