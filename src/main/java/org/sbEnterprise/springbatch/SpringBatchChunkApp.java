package org.sbEnterprise.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sbEnterprise.chunkBatchComponents.SpringBatchChunkConfig;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBatchChunkApp {
    private static final Logger logger = LogManager.getLogger(SpringBatchChunkApp.class.getName());

    public static void main(String[] args) throws InterruptedException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        SpringApplication.run(SpringBatchChunkApp.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringBatchChunkConfig.class);
        SimpleJobOperator jobOperator = context.getBean("jobOperator", SimpleJobOperator.class);
        Long executionID = jobOperator.start("mockJob", "");

    }

}
