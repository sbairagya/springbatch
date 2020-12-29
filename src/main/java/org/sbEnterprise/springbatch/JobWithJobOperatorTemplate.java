package org.sbEnterprise.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public abstract class JobWithJobOperatorTemplate {
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public JobLauncher jobLauncher;
    @Autowired
    public JobRegistry jobRegistry;
    @Autowired
    public JobRepository jobRepository;
    @Autowired
    public JobExplorer jobExplorer;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;


    @Bean
    public ProcessShutdownListener shutdownListener() throws Exception {
        ProcessShutdownListener processShutdownListener = new ProcessShutdownListener();
        processShutdownListener.setJobOperator(jobOperator());
        return processShutdownListener;
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


    @Bean
    public JobOperator asyncJobOperator() throws Exception {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        //Create and tie the operator with a jobLauncher running async taskExecutor
        SimpleJobLauncher asyncJobLauncher = new SimpleJobLauncher();
        asyncJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor("asyncThread-"));
        asyncJobLauncher.setJobRepository(jobRepository);
        jobOperator.setJobLauncher(asyncJobLauncher);

        jobOperator.setJobRepository(jobRepository);
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.afterPropertiesSet();
        return jobOperator;
    }
    @Bean
    public JobExplorer jobExecution(){
        return this.jobExplorer;
    }
    /**
     * needed to provide joboperator the required registry
     *
     * @param jobRegistry
     * @return
     */
    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
