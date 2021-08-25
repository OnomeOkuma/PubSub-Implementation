package assessment.publisher.controllers;

import assessment.publisher.model.SubscribeDTO;
import assessment.publisher.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("subscribe")
public class SubscribeController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscribeController (SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/{topic}")
    public ResponseEntity<Object> subscriber(@PathVariable String topic, @RequestBody SubscribeDTO payload) throws Exception {
        if(!topic.matches("([a-zA-Z])\\w+")) {
            return ResponseEntity.badRequest().body("Invalid Topic");
        }

        subscriberService.subscribe(topic, payload.url);
        payload.topic = topic;
        return ResponseEntity.ok(payload);
    }
}
