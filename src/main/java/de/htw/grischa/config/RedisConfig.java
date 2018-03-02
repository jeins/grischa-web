package de.htw.grischa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import de.htw.grischa.controllers.DataController;
import de.htw.grischa.models.Worker;
import de.htw.grischa.models.WorkerLocation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public Jackson2JsonRedisSerializer<Worker> messageSerializer() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());

        Jackson2JsonRedisSerializer<Worker> serializer = new Jackson2JsonRedisSerializer<>(Worker.class);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    MessageListenerAdapter redisSubscriberAdapter(DataController dataController) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(dataController, "handleMessage");
        adapter.setSerializer(messageSerializer());

        return adapter;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, DataController dataController) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(redisSubscriberAdapter(dataController), new PatternTopic(DataController.REDIS_CHANNEL));

        return container;
    }

    @Bean
    RedisTemplate<String, WorkerLocation> redisTemplateWorkerLocation(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, WorkerLocation> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
