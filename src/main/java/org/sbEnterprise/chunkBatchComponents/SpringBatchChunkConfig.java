package org.sbEnterprise.chunkBatchComponents;

import org.sbEnterprise.springbatch.JobWithJobOperatorTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SpringBatchChunkConfig extends JobWithJobOperatorTemplate {

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
        return this.stepBuilderFactory.get("mockStep").<String, String>chunk(2)
                .reader(new MockReader())
                .processor(new MockProcessor())
                .writer(new MockWriter()).build();
    }

}
