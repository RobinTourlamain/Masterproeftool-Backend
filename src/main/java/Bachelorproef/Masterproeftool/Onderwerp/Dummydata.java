package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruikerservice;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;

@Configuration
@Order(1)
class Dummydata {

    private static final Logger log = LoggerFactory.getLogger(Dummydata.class);

    @Bean
    CommandLineRunner initDatabase(Onderwerprepository repository, Gebruikerservice gebruikerservice) {
        //voeg onderwerpen toe naar wens
        return args -> {
            log.info("Preloading " + repository.save(new Onderwerp("gebtest","doelgroep",gebruikerservice.findByUsername("Coordinator"),"email","phone",1,"Beschrijving",new ArrayList<String>(),new ArrayList<String>(),true)));
            log.info("Preloading " + repository.save(new Onderwerp("IT","doelgroep",gebruikerservice.findByUsername("Coordinator"),"email","phone",1,"Beschrijving",new ArrayList<String>(),new ArrayList<String>(),true)));
            log.info("Preloading " + repository.save(new Onderwerp("A","doelgroep",gebruikerservice.findByUsername("Coordinator"),"email","phone",1,"Beschrijving",new ArrayList<String>(),new ArrayList<String>(),true)));
        };
    }
}
