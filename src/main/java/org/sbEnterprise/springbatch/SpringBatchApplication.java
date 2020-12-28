package org.sbEnterprise.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sbEnterprise.SampleBatchComponents.SampleBatchJobConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBatchApplication {
	private static final Logger logger = LogManager.getLogger(SpringBatchApplication.class.getName());

	public static void main(String[] args) throws InterruptedException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
		SpringApplication.run(SpringBatchApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext(SampleBatchJobConfiguration.class);
		//JobExecution jobExecution = context.getBean("jobLauncher",JobLauncher.class).run(context.getBean("job", Job.class), new JobParametersBuilder().toJobParameters());
		SimpleJobOperator jobOperator =context.getBean("jobOperator", SimpleJobOperator.class);
		Long executionID = jobOperator.start("mockJob","");
	}

}
