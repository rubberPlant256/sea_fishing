package ru.rsreu.sea_fishing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.rsreu.sea_fishing.configs.MongoProperties;

@SpringBootApplication
@EnableConfigurationProperties(MongoProperties.class)
public class SeaFishingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeaFishingApplication.class, args);
    }

}
