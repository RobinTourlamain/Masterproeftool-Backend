package Bachelorproef.Masterproeftool.Onderwerp;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Dummydata {

    private static final Logger log = LoggerFactory.getLogger(Dummydata.class);

    @Bean
    CommandLineRunner initDatabase(Onderwerprepository repository) {
        //voeg onderwerpen toe naar wens
        return args -> {
            log.info("Preloading " + repository.save(new Onderwerp("Elektronica")));
            log.info("Preloading " + repository.save(new Onderwerp("IT")));
        };
    }
}
