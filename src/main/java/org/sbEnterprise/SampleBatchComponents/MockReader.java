package org.sbEnterprise.SampleBatchComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MockReader implements ItemReader<String> {
    Logger logger = LogManager.getLogger(MockReader.class.getName());

    private final static String inputString = "small case string";
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        logger.debug("output from read{}",inputString);
        return inputString;
    }
}
