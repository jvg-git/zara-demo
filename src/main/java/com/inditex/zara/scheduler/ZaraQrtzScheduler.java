package com.inditex.zara.scheduler;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import com.inditex.zara.config.ZaraBeanJobFactory;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.JobBuilder.*;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
public class ZaraQrtzScheduler {

	private static Logger logger = LoggerFactory.getLogger(ZaraQrtzScheduler.class);
	
	@Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Using Quartz Scheduler...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        ZaraBeanJobFactory jobFactory = new ZaraBeanJobFactory();
        logger.debug("Configuring Job factory");
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
        logger.debug("Getting a handle to the Scheduler");
        Scheduler scheduler = factory.getScheduler();
        if (scheduler.checkExists(job.getKey())){
            scheduler.deleteJob(job.getKey());
        }
        scheduler.scheduleJob(job, trigger);
        logger.debug("Starting Scheduler threads");
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public JobDetail jobDetail() {
        return newJob().ofType(UpdateJob.class)
        		.storeDurably().withIdentity(JobKey.jobKey("Zara_Qrtz_Job_Detail"))
        		.withDescription("Invoke Update Job service...").build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        int frequencyInSec = 10;
        logger.info("Configuring trigger to fire every {} seconds", frequencyInSec);
        return newTrigger().forJob(job)
        	      .withIdentity("Qrtz_Trigger")
        	      .withDescription("Sample trigger")
        	      .withSchedule(simpleSchedule().repeatForever().withIntervalInMinutes(2))
        	      .build();
    }
	
}
