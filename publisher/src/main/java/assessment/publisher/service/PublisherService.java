package assessment.publisher.service;


import assessment.publisher.model.PublishDTO;
import assessment.publisher.repository.RedisDAO;
import assessment.publisher.service.tasks.PublisherTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
public class PublisherService {

    private final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    private final AsyncTaskExecutor taskExecutor;

    private final RedisDAO redisDAO;

    private final RestTemplate client;

    @Value("${publish.timeout}")
    private int timeout;

    @Value("${publish.success.minimum.subscribers}")
    private int minimumNumOfSuccessPublish;

    @Autowired
    public PublisherService(AsyncTaskExecutor taskExecutor, RedisDAO redisDAO, RestTemplateBuilder builder) {
        this.redisDAO = redisDAO;
        this.taskExecutor = taskExecutor;
        this.client = builder.build();
    }


    public void publishMessage(String topic, String payload) throws Exception {
        Set<String> urls;
        try {
            urls = this.redisDAO.getUrls(topic);
            if (urls == null)
                return;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }


        List<Future<Boolean>> allTasks = new ArrayList<>();
        PublishDTO payloadDTO = new PublishDTO(topic, payload);
        urls.forEach(url -> {
            PublisherTask task = new PublisherTask(client, url, payloadDTO);
            allTasks.add(this.taskExecutor.submit(task));
        });

        List<Future<Boolean>> successTasks = allTasks.stream().filter(this::checkTaskSuccess).collect(Collectors.toList());
        logger.info("{} servers notified out of {} servers", successTasks.size(), allTasks.size());

        if(successTasks.size() < minimumNumOfSuccessPublish)
            throw new Exception("Failed to Publish message");

    }

    private Boolean checkTaskSuccess(Future<Boolean> task) {
        try {
            return task.get(timeout, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
