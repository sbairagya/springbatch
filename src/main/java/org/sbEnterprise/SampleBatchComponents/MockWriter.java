package org.sbEnterprise.SampleBatchComponents;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MockWriter implements ItemWriter{
    Logger logger = LogManager.getLogger(MockProcessor.class.getName());


    @Override
    public void write(List list) throws Exception {
        logger.debug("Writer output{}",list.toArray());
    }
}
