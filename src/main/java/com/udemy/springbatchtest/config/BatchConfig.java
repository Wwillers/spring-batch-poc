package com.udemy.springbatchtest.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

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
                   .incrementer(new RunIdIncrementer())
                   .build();
    }

    private Step printHelloWorld() {
        return stepBuilderFactory
                   .get("printHelloWorld")
                   .tasklet(helloWorldTasklet(null))
                   .build();
    }

    @Bean
    @StepScope
    public Tasklet helloWorldTasklet(@Value("#{jobParameters['name']}") String name) {
        return (stepContribution, chunkContext) -> {
            System.out.printf("Hello World, %s!%n", name);
            return RepeatStatus.FINISHED;
        };
    }
}
