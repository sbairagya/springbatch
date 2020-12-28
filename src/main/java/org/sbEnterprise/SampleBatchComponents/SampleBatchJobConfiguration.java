package org.sbEnterprise.SampleBatchComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sbEnterprise.springbatch.ProcessShutdownListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SampleBatchJobConfiguration {
    private static final Logger logger = LogManager.getLogger(SampleBatchJobConfiguration.class.getName());

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobRegistry jobRegistry;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(Step mockStep) throws Exception {
        return this.jobBuilderFactory.get("mockJob")
                .start(mockStep)
                .preventRestart()
                .listener(shutdownListener())
                .build();
    }
//https://www.baeldung.com/spring-batch-tasklet-chunk
    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("mockStep").<String, String>chunk(1)
                .reader(new MockReader())
                .processor(new MockProcessor())
                .writer(new MockWriter()).build();
    }
    @Bean
    public JobOperator jobOperator() throws Exception {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobLauncher(jobLauncher);
        jobOperator.setJobRepository(jobRepository);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.afterPropertiesSet();
        return jobOperator;
    }

    /**
     * needed to provide joboperator the required registry
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public ProcessShutdownListener shutdownListener() throws Exception {
        ProcessShutdownListener processShutdownListener = new ProcessShutdownListener();
        processShutdownListener.setJobOperator(jobOperator());
        return processShutdownListener;
    }
}
