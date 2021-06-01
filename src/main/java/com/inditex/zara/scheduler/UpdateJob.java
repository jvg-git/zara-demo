package com.inditex.zara.scheduler;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.inditex.zara.model.PriceUpdate;
import com.inditex.zara.services.UpdateJobService;
import com.inditex.zara.services.async.ZaraAsyncPriceService;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
public class UpdateJob implements Job {

	private static Logger logger = LoggerFactory.getLogger(UpdateJob.class);
	private static String fileName = "prices-update.csv";
    
	@Autowired
    private UpdateJobService jobService;

	@Autowired
	private ZaraAsyncPriceService priceService;
	
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
       	try {
			updateDb();
		} catch (IllegalStateException | IOException e) {
			logger.error("Error actualizando los precios: {}", ToStringBuilder.reflectionToString(e, ToStringStyle.DEFAULT_STYLE));
		}
        jobService.executeUpdateJob();
        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }
    
    private void updateDb() throws IllegalStateException, IOException {
    	File csvFile = new ClassPathResource(fileName, this.getClass().getClassLoader()).getFile();
    	
    	List<PriceUpdate> beans = new CsvToBeanBuilder<PriceUpdate>(new FileReader(csvFile.getAbsoluteFile()))
                .withType(PriceUpdate.class).build().parse();
    	beans.stream().forEach((p) -> {
    		if (p.getNeedSync() > 0) {
    			priceService.updatePrice(p);
    		} else {
    			logger.info("El precio esta actualizado...no se requieren acciones adicionales...");
    		}
    	});
    }
}