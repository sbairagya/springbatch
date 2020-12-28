package org.sbEnterprise.SampleBatchComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class MockReader implements ItemReader<String> {
    private static final Logger logger = LogManager.getLogger(MockReader.class.getName());


    private final static String inputString = "small case string";
    private static int recordCounter = 0;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(recordCounter < 10) {
            logger.debug("Returning record #{} from read{}", recordCounter,inputString);
            recordCounter ++;
            return inputString;
        }else
        {
            logger.debug("End of record", inputString);
            return null;
        }
    }
}
