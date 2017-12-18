package de.htw.grischa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import de.htw.grischa.controllers.WorkerLocationController;
import de.htw.grischa.events.MessageBroadcaster;
import de.htw.grischa.events.RedisEventListener;
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
    public Jackson2JsonRedisSerializer<Worker> messageRedisSerializer() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());

        Jackson2JsonRedisSerializer<Worker> serializer = new Jackson2JsonRedisSerializer<>(Worker.class);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisEventListener receiverService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiverService, "handleMessage");
        adapter.setSerializer(messageRedisSerializer());

        return adapter;
    }

    @Bean
    MessageListenerAdapter messageBroadcasterAdapter(MessageBroadcaster messageBroadcaster){
        MessageListenerAdapter adapter = new MessageListenerAdapter(messageBroadcaster, "handleMessage");
        adapter.setSerializer(messageRedisSerializer());

        return adapter;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, RedisEventListener eventListener, MessageBroadcaster broadcastListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter(eventListener), new PatternTopic(RedisEventListener.EVENT_RECEIVE_MESSAGE_KEY));
        container.addMessageListener(messageBroadcasterAdapter(broadcastListener), new PatternTopic(RedisEventListener.EVENT_RECEIVE_MESSAGE_KEY));

        return container;
    }

    @Bean
    RedisTemplate<String, Worker> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Worker> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(messageRedisSerializer());

        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, WorkerLocation> redisTemplateWorkerLocation(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, WorkerLocation> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
