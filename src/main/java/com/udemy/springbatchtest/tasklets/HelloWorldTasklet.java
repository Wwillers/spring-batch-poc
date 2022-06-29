package com.udemy.springbatchtest.tasklets;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class HelloWorldTasklet implements Tasklet {

    @Value("#{jobParameters['name']}")
    public String name;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        return helloWorldTasklet(this.name);
    }

    public RepeatStatus helloWorldTasklet(String name) {
        System.out.printf("Hello World, %s!%n", name);
        return RepeatStatus.FINISHED;
    }
}
