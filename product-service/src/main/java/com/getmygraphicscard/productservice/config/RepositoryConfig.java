package com.getmygraphicscard.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.getmygraphicscard.productservice")
class RepositoryConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "Java";
    }
}
