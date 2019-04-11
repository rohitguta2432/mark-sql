package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author Rohit Raj
 *
 */

@SpringBootApplication
public class MarkRestApplication extends SpringBootServletInitializer{
  public static void main(String[] args) {
	//System.setProperty("spring.devtools.restart.enabled", "false");
    SpringApplication.run(MarkRestApplication.class, args);
  }
  
  @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MarkRestApplication.class);
	}
}
