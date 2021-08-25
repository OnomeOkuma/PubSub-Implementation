package assessment.publisher.service.tasks;

import assessment.publisher.model.PublishDTO;
import assessment.publisher.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class PublisherTask implements Callable<Boolean> {
    private Logger logger = LoggerFactory.getLogger(PublisherTask.class);

    private RestTemplate webClient;
    private PublishDTO requestPayload;
    private String url;

    public PublisherTask(RestTemplate webClient, String url, PublishDTO body) {
        this.webClient = webClient;
        this.requestPayload = body;
        this.url = url;
    }

    @Override
    public Boolean call() {
        ResponseEntity<String> response = webClient.postForEntity(url, requestPayload, String.class);
        logger.info("Status Code: {}, URL: {}, requestPayload: {}", response.getStatusCodeValue(), url, requestPayload.toString());
        return response.getStatusCode() == HttpStatus.OK;
    }


}
