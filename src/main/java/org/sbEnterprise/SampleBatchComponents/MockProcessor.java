package org.sbEnterprise.SampleBatchComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import java.util.Locale;

public class MockProcessor implements ItemProcessor<String, String> {
    Logger logger = LogManager.getLogger(MockProcessor.class.getName());

    @Override
    public String process(String s) throws Exception {
        logger.debug("output from process{}",s);
       // System.out.println("Sanku");
        Thread.sleep(50);
        return s.toUpperCase(Locale.ROOT);
    }
}
