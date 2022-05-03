package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean //Dummydata
    CommandLineRunner initRollen(Rolservice rolservice) {
        return args -> {
            log.info("Preloading " + rolservice.saveRol(new Rol("Admin")));
            log.info("Preloading " + rolservice.saveRol(new Rol( "Student")));
            log.info("Preloading " + rolservice.saveRol(new Rol("Coordinator")));
            log.info("Preloading " + rolservice.saveRol(new Rol("Promotor")));

            log.info("Preloading " + rolservice.saveGebruiker(new Gebruiker( "Robin","Robin","root", new ArrayList<>())));
            log.info("Preloading " + rolservice.saveGebruiker(new Gebruiker( "Admin", "Admin", "root", new ArrayList<>())));
            log.info("Preloading " + rolservice.saveGebruiker(new Gebruiker( "Coordinator","Coordinator","root", new ArrayList<>())));
            log.info("Preloading " + rolservice.saveGebruiker(new Student( 69,"student","studentemail","root")));

            rolservice.addRolToGebruiker("Admin", "Admin");
            rolservice.addRolToGebruiker("Admin", "Student");
            rolservice.addRolToGebruiker("Robin", "Student");
            rolservice.addRolToGebruiker("Coordinator", "Coordinator");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
