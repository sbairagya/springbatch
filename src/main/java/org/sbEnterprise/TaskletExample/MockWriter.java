package org.sbEnterprise.TaskletExample;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class MockWriter implements Tasklet, StepExecutionListener {
    Logger logger = LogManager.getLogger(MockProcessor.class.getName());

    private List<String> outputString;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        outputString= (List<String>) stepExecution
                .getJobExecution()
                .getExecutionContext()
                .get("processed_records");
    }
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        outputString.forEach(s -> {
            logger.debug("Writer output: {}", s);
        });
        return RepeatStatus.FINISHED;
    }
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Writer output finished");
        return ExitStatus.COMPLETED;
    }


}
