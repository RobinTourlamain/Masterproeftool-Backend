package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Company;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Promotor;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerpservice;
import com.github.javafaker.Faker;
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

//    @Bean //Dummydata
//    CommandLineRunner initRollen(Rolservice rolservice, Gebruikerrepository gebruikerrepository, Onderwerpservice onderwerpservice) {
//        return args -> {
//            Faker f = new Faker();
//            log.info("Preloading " + rolservice.saveRol(new Rol("Admin")));
//            log.info("Preloading " + rolservice.saveRol(new Rol( "Student")));
//            log.info("Preloading " + rolservice.saveRol(new Rol("Coordinator")));
//            log.info("Preloading " + rolservice.saveRol(new Rol("Promotor")));
//            log.info("Preloading " + rolservice.saveRol(new Rol("Bedrijf")));
//
//            rolservice.saveGebruiker(new Student( "Tourlamain","Remail","root", new ArrayList<>(),"Robin"));
//            rolservice.saveGebruiker(new Gebruiker( "Admin", "Admin", "root", new ArrayList<>()));
//            rolservice.saveGebruiker(new Gebruiker( "Coordinator","Coordinator","root", new ArrayList<>()));
//            rolservice.saveGebruiker(new Student( "achternaam","email","root", new ArrayList<>(), "voornaam"));
//            rolservice.saveGebruiker(new Company( "Telenet","telenetemail","root", new ArrayList<>(),"Brahm","Gert",  "phone",  "sector",  "address",  "website"));
//            rolservice.saveGebruiker(new Promotor( "Vorstermans","amail","root", new ArrayList<>(),"annemie"));
//            rolservice.saveGebruiker(new Promotor("Wauters", "tony.wauters@kuleuven.be", "root", new ArrayList<>(), "Tony"));
//
//
//            rolservice.addRolToGebruiker("Admin", "Admin");
//            rolservice.addRolToGebruiker("Admin", "Student");
//            rolservice.addRolToGebruiker("Remail", "Student");
//            rolservice.addRolToGebruiker("Coordinator", "Coordinator");
//            rolservice.addRolToGebruiker("email", "Student");
//            rolservice.addRolToGebruiker("tony.wauters@kuleuven.be", "Promotor");
//            rolservice.addRolToGebruiker("telenetemail", "Bedrijf");
//            rolservice.addRolToGebruiker("amail", "Promotor");
//
//            for(int i = 0; i<20; i++){
//                String fn = f.name().firstName();
//                String un = f.internet().emailAddress(fn);
//                String sn = "r" + f.number().digits(7);
//                rolservice.saveGebruiker(new Student( f.name().lastName(),un,"root", new ArrayList<>(),fn,sn,0,0,f.address().fullAddress()));
//                rolservice.addRolToGebruiker(un, "Student");
//            }
//
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
