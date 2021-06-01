package com.inditex.zara.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateJobService {

	private static Logger logger = LoggerFactory.getLogger(UpdateJobService.class);

	public static final long EXECUTION_TIME = 5000L;


    private AtomicInteger count = new AtomicInteger();

    public void executeUpdateJob() {

        logger.info("The sample job service has begun...");
        try {
            Thread.sleep(EXECUTION_TIME);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample job service: ", e);
        } finally {
            count.incrementAndGet();
            logger.info("Sample job service has finished...");
        }
    }

    public int getNumberOfInvocations() {
        return count.get();
    }
}
