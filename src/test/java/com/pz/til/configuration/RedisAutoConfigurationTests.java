package com.pz.til.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO: https://spring.io/blog/2018/03/07/testing-auto-configurations-with-spring-boot-2-0
 */
class RedisAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(RedisConfig.class, RedisAutoConfiguration.class));


    @Test
    void redisContextInitialized_shouldHaveTemplateBean_whenRegistered() {
        this.contextRunner.withPropertyValues("til.datasource=redis")
                .run(context -> assertThat(context)
                .getBean(ReactiveRedisTemplate.class)
                .isNotNull());
    }


    @Test
    void givenDataSourcePropertiesIsNotSet_whenContextIsCreated_thenShouldNotContainTheTemplateBean() {
        this.contextRunner.run(context -> assertThat(context)
                .doesNotHaveBean(ReactiveRedisTemplate.class));
    }

}
