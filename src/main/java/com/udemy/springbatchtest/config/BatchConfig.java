package com.udemy.springbatchtest.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    public Job printEvenOdd() {
        return jobBuilderFactory
                   .get("printEvenOdd")
                   .start(printEvenOddStep())
                   .incrementer(new RunIdIncrementer())
                   .build();
    }

    public Step printEvenOddStep() {
        return stepBuilderFactory
                   .get("printEvenOddStep")
                   .<Integer, String> chunk(1)
                   .reader(countUntilTenReader())
                   .processor(evenOrOddProcessor())
                   .writer(printWriter())
                   .build();
    }

    public IteratorItemReader<Integer> countUntilTenReader() {
        Stream<Integer> numbers = generateNumbers();
        return new IteratorItemReader<>(numbers.iterator());
    }

    public FunctionItemProcessor<Integer, String> evenOrOddProcessor() {
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ? format("Item %s é Par", item) : format("Item %s é Impar", item));
    }

    public ItemWriter<String> printWriter() {
        return itens -> itens.forEach(System.out::println);
    }

    private Stream<Integer> generateNumbers() {
        return IntStream.rangeClosed(1, 10).boxed();
    }
}
