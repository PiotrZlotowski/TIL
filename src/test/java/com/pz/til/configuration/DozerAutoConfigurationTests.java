package com.pz.til.configuration;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class DozerAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DozerConfig.class));

    @Test
    void dozerConfiguration_shouldHaveOneBean_whenRegistered() {
        this.contextRunner.run(context -> assertThat(context)
                .hasNotFailed()
                .hasSingleBean(DozerBeanMapper.class)
                .getBean(DozerBeanMapper.class)
                .hasFieldOrProperty("mappingFiles"));
    }


}
