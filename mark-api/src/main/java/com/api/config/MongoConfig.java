/*package com.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.api.dao.DaoPackage;
import com.mongodb.MongoClient;

*//**
 * @author Rohit Raj
 *
 *//*
@Configuration
@EnableMongoRepositories(basePackageClasses = DaoPackage.class)
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${mongo.db.name}")
	private String mongoDBName;

	@Value("${mongo.db.host}")
	private String mongoHost;

	@Value("${mongo.db.u.key}")
	private String userName;

	@Value("${mongo.db.p.value}")
	private String pValue;

	@Value("${mongo.db.port}")
	private Integer port;

	@Override
	protected String getDatabaseName() {
		return mongoDBName;
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.api.models";
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

	@Override
	public MongoClient mongoClient() {
		return new MongoClient(mongoHost);
//		MongoCredential credential = MongoCredential.createCredential(userName, mongoDBName, pValue.toCharArray());
//		MongoClient mongoClient = new MongoClient(new ServerAddress(mongoHost, 27017), Arrays.asList(credential));
//		return mongoClient;
	}
}
*/