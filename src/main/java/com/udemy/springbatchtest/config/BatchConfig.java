package com.udemy.springbatchtest.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory
                   .get("helloWorldJob")
                   .start(printHelloWorld())
                   .build();
    }

    private Step printHelloWorld() {
        return stepBuilderFactory
                   .get("printHelloWorld")
                   .tasklet((stepContribution, chunkContext) -> {
                       System.out.println("Hello World!");
                       return RepeatStatus.FINISHED;
                   })
                   .build();
    }
}
