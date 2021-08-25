package assessment.subscriber;

import assessment.subscriber.model.SubscriberRequestDTO;
import assessment.subscriber.model.SubscribeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class Config {

    private Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${publisher.url}")
    private String publisherUrl;

    @Value("${subscriber.urls}")
    private List<String> subscriberUrls;

    private RestTemplateBuilder builder;

    private ConfigurableApplicationContext context;

    @Autowired
    public Config(RestTemplateBuilder builder, ConfigurableApplicationContext  ctx) {
        this.builder = builder;
        this.context = ctx;
    }

    @Bean
    public RestTemplate getHttpClient() {
        return builder.build();
    }


    @PostConstruct
    public void registerSubscriber() {

        try {

            subscriberUrls.forEach(url -> {
                ResponseEntity<SubscribeResponseDTO> response = getHttpClient().postForEntity(publisherUrl
                        , new SubscriberRequestDTO(url), SubscribeResponseDTO.class);

                if (response.getStatusCode() != HttpStatus.OK) {
                    logger.error("Unable to connect to publisher: Response - {}", response.getStatusCodeValue());
                    logger.info(".........Exiting Application...........");

                    SpringApplication.exit(context, () -> {
                        return 0;
                    });

                    System.exit(0);
                }

                logger.info("Response: {} {}", response.getBody().topic, response.getBody().url);
            });


            logger.info("........Successfully Subscribed to publisher.............");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info(".........Exiting Application...........");

            SpringApplication.exit(context, () -> {
                return 0;
            });

            System.exit(0);
        }
    }


}
