package assessment.publisher.service;

import assessment.publisher.repository.RedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class SubscriberService {

    private Logger logger = LoggerFactory.getLogger(SubscriberService.class);
    private RedisDAO redisDAO;

    @Autowired
    public SubscriberService(RedisDAO redisDAO) {
        this.redisDAO = redisDAO;
    }


    public void subscribe(String topic, String url) throws Exception{
        logger.info("Subscribing to topic: {} with url: {}", topic, url);

        try {
            synchronized (topic) {
                HashSet<String> urls = redisDAO.getUrls(topic);
                if (urls == null) {
                    urls = new HashSet<>();
                    urls.add(url);
                    redisDAO.save(topic, urls);

                } else {
                    urls.add(url);
                    redisDAO.save(topic, urls);
                }
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            throw exception;
        }

    }

}
