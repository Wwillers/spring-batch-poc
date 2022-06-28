package com.udemy.springbatchtest.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class HelloWorldJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    public HelloWorldJobConfig(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Job helloWorldJob(Step printHelloWorldStep) {
        return jobBuilderFactory
                   .get("helloWorldJob")
                   .start(printHelloWorldStep)
                   .incrementer(new RunIdIncrementer())
                   .build();
    }
}
