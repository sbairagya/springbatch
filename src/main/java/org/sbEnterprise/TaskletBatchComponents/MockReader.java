package org.sbEnterprise.TaskletBatchComponents;

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

public class MockReader implements Tasklet, StepExecutionListener {
    private static final Logger logger = LogManager.getLogger(org.sbEnterprise.ChunkBatchComponents.MockReader.class.getName());
    private final static String inputString = "small case string";
    private static int recordCounter;
    private List<String> returnStringList;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Before Step: Setting up record count to 0");
        recordCounter = 0;
        returnStringList = new ArrayList<>();
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        logger.debug("MockReader is in Execute step");

        if (recordCounter < 10) {
            String newRecord = inputString + "_" + recordCounter;
            logger.debug("Returning record #{} from read{}", recordCounter, newRecord);
            returnStringList.add(newRecord);
            recordCounter = recordCounter + 1;
            return RepeatStatus.CONTINUABLE;
        } else {
            logger.debug("End of record", inputString);
            return RepeatStatus.FINISHED;
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Saving output record to Execution Context {}", returnStringList);
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("new_records", returnStringList);
        return ExitStatus.COMPLETED;
    }
}
