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


}
