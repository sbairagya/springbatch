package org.sbEnterprise.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sbEnterprise.chunkBatchComponents.SpringBatchChunkConfig;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
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
public class SpringAsyncBatchChunkApp {
    private static final Logger logger = LogManager.getLogger(SpringAsyncBatchChunkApp.class.getName());

    public static void main(String[] args) throws InterruptedException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException, NoSuchJobInstanceException {
        SpringApplication.run(SpringAsyncBatchChunkApp.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringBatchChunkConfig.class);
        //obtain Simple JobOperator with Async Executor from bean.
        SimpleJobOperator jobOperator = context.getBean("asyncJobOperator", SimpleJobOperator.class);
        Long executionID = jobOperator.start("mockJob", "");
        logger.debug("Started executing the job asynchronously -- execution ID:{}", executionID);
        //Get the jobExplorer associated with that jobOperator
        JobExplorer explorer = context.getBean("jobExplorer", JobExplorer.class);
        while(true) {
            JobExecution execution1 = explorer.getJobExecution(executionID);
            BatchStatus status = execution1.getStatus();
            logger.debug("Status of job {} :{} :: Exit status:{}", execution1.getJobInstance().getJobName(), execution1.getStatus(), execution1.getExitStatus());
            if(!status.isRunning())
                break;
            if(execution1.getStatus().isRunning()) {
                Thread.sleep(800);
                logger.debug("Initiating stop");
                jobOperator.stop(executionID);
            }
        }
        //jobOperator.stop(executionID);
        logger.debug("Completed executing the job asynchronously -- execution ID:{}", executionID);
    }

}
