package com.pz.til.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * Created by piotr on 12/07/2017.
 */
@Configuration
@EnableRedisRepositories
@ConditionalOnProperty(value = "til.datasource", havingValue = "redis")
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
            ResourceLoader resourceLoader) {
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(
                resourceLoader.getClassLoader());
        RedisSerializationContext<Object, Object> serializationContext = RedisSerializationContext
                .newSerializationContext().key(jdkSerializer).value(jdkSerializer)
                .hashKey(jdkSerializer).hashValue(jdkSerializer).build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory,
                serializationContext);
    }

}
