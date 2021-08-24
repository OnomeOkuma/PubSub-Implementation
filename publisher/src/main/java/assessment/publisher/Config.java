package assessment.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashSet;

@Configuration
public class Config {

    private RedisConnectionFactory connectionFactory;

    @Autowired
    public Config(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public ThreadPoolTaskExecutor getTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

}
