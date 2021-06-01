package com.inditex.zara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.inditex.zara.repository.base.PriceJpaRepositoryImpl;

@EnableConfigurationProperties
@EnableAsync
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = { "com.inditex.zara.entity" })
@EnableJpaRepositories(basePackages = { "com.inditex.zara.repository" }, repositoryBaseClass = PriceJpaRepositoryImpl.class)
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
