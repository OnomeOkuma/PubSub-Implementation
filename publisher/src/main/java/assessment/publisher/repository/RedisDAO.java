package assessment.publisher.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RedisDAO {

    private StringRedisTemplate template;

    @Autowired
    public RedisDAO(StringRedisTemplate template) {
        this.template = template;
        Jackson2JsonRedisSerializer<HashSet> serializer = new Jackson2JsonRedisSerializer<HashSet>(HashSet.class);
        this.template.setValueSerializer(serializer);
        this.template.setHashValueSerializer(serializer);
    }

    public void save(String topic, HashSet<String> urls) {
        template.opsForHash().put(topic, topic, urls);
    }

    public HashSet<String> getUrls(String topic) {
        return (HashSet<String>) template.opsForHash().get(topic, topic);
    }
}
