package org.sbEnterprise.TaskletExample;

import org.sbEnterprise.springbatch.JobWithJobOperatorTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;


public class SampleJobWithTasklet extends JobWithJobOperatorTemplate {

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory
                .get("mockTaskletJob")
                .start(readLine())
                .next(processLine())
                .next(writeLine())
                .build();
    }

    @Bean
    public Step readLine() {
        return this.stepBuilderFactory
                .get("mockReader")
                .tasklet(new MockReader())
                .build();
    }
    @Bean
    public Step processLine() {
        return this.stepBuilderFactory
                .get("MockProcessor")
                .tasklet(new MockProcessor())
                .build();
    }
    @Bean
    public Step writeLine() {
        return this.stepBuilderFactory
                .get("mockWriter")
                .tasklet(new MockWriter())
                .build();
    }

}
