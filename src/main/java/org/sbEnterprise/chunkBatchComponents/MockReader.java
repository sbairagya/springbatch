package org.sbEnterprise.chunkBatchComponents;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;

public class MockReader implements ItemReader<String> {
    private static final Logger logger = LogManager.getLogger(MockReader.class.getName());


    private final static String inputString = "small case string";
    private static int recordCounter = 0;

    @Override
    public String read() throws Exception {
        if (recordCounter < 10) {
            String s = inputString + "_" + recordCounter;
            logger.debug("Returning record #{} from read{}", recordCounter, s);
            Thread.sleep(200);
            recordCounter++;
            return s;
        } else {
            logger.debug("End of record", inputString);
            return null;
        }
    }
}
