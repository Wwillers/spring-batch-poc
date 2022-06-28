package com.udemy.springbatchtest.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldStepConfig {

    private final StepBuilderFactory stepBuilderFactory;

    public HelloWorldStepConfig(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step printHelloWorldStep(Tasklet helloWorldTasklet) {
        return stepBuilderFactory
                   .get("printHelloWorldStep")
                   .tasklet(helloWorldTasklet)
                   .build();
    }
}
