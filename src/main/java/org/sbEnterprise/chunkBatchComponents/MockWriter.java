package org.sbEnterprise.chunkBatchComponents;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class MockWriter implements ItemWriter {
    Logger logger = LogManager.getLogger(MockProcessor.class.getName());


    @Override
    public void write(List list) throws Exception {
        list.forEach(s -> {
            logger.debug("Writer output:{}", s.toString());
        });
    }
}
