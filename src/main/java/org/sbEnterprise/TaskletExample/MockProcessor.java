package org.sbEnterprise.TaskletExample;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockProcessor implements Tasklet, StepExecutionListener {
    Logger logger = LogManager.getLogger(MockProcessor.class.getName());
    private List<String> inputString;
    private List<String> outputString;
    private int recordIndx = 0;
    @Override
    public void beforeStep(StepExecution stepExecution) {
        inputString = (List<String>) stepExecution
                .getJobExecution()
                .getExecutionContext().get("new_records");
        logger.debug("Input to MockProcessor:{}", inputString.toArray().length);
        outputString=new ArrayList<>();
        recordIndx = 0;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        if(recordIndx < inputString.size()){
            logger.debug("Executing processing on:{}", inputString.get(recordIndx));
            outputString.add(inputString.get(recordIndx).toUpperCase(Locale.ROOT));
            recordIndx++;
            Thread.sleep(50);
            return RepeatStatus.CONTINUABLE;
        }else
            return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Mock Processor ended.");
        stepExecution.getJobExecution().getExecutionContext().put("processed_records", outputString);
        return null;
    }
}
