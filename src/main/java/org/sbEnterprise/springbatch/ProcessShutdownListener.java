package org.sbEnterprise.springbatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;

public class ProcessShutdownListener implements JobExecutionListener {
    private static final Logger logger = LogManager.getLogger(ProcessShutdownListener.class.getName());

    private JobOperator jobOperator;

    @Override
    public void afterJob(JobExecution jobExecution) { /* do nothing. */ }

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    jobOperator.stop(jobExecution.getId());
                    while (jobExecution.isRunning()) {
                        logger.info("waiting for job to stop...");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                    }
                } catch (NoSuchJobExecutionException e) { // ignore
                } catch (JobExecutionNotRunningException e) { // ignore
                }
            }
        });
    }

    public void setJobOperator(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }
}
