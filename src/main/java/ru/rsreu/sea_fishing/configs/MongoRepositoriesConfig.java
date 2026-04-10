package ru.rsreu.sea_fishing.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Profile("mongo")
@EnableMongoRepositories(basePackages = "ru.rsreu.sea_fishing.mongo.repository")
public class MongoRepositoriesConfig {
}
