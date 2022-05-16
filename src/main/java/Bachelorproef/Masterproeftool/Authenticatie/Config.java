package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Company;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Coordinator;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Promotor;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerprepository;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerpservice;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Order(1)
    @Bean
    CommandLineRunner initRollen(Rolservice rolservice, Gebruikerrepository gebruikerrepository, Onderwerpservice onderwerpservice) {
        return args -> {
            Faker f = new Faker();
            log.info("Preloading " + rolservice.saveRol(new Rol("Admin")));
            log.info("Preloading " + rolservice.saveRol(new Rol( "Student")));
            log.info("Preloading " + rolservice.saveRol(new Rol("Coordinator")));
            log.info("Preloading " + rolservice.saveRol(new Rol("Promotor")));
            log.info("Preloading " + rolservice.saveRol(new Rol("Bedrijf")));

            rolservice.saveGebruiker(new Student( "Tourlamain","robin.tourlamain@student.kuleuven.be","root", new ArrayList<>(),"Robin"));
            rolservice.saveGebruiker(new Student( "De Leersnyder","fien.deleersnyder@student.kuleuven.be","root", new ArrayList<>(),"Fien"));
            rolservice.saveGebruiker(new Student( "Valckenier","emmy.valckenier@student.kuleuven.be","root", new ArrayList<>(),"Emmy"));
            rolservice.saveGebruiker(new Gebruiker( "Admin", "Admin", "root", new ArrayList<>()));
            rolservice.saveGebruiker(new Coordinator( "Coordinator","Coordinator","root", new ArrayList<>(),"naam","phone",0,0 ));
            rolservice.saveGebruiker(new Student( "achternaam","email","root", new ArrayList<>(), "voornaam"));
            rolservice.saveGebruiker(new Company( "Telenet","telenetemail","root", new ArrayList<>(),"Brahm","Gert",  "phone",  "sector",  "address",  "website"));
            rolservice.saveGebruiker(new Promotor( "Vorstermans","amail","root", new ArrayList<>(),"annemie"));
            rolservice.saveGebruiker(new Promotor("Wauters", "tony.wauters@kuleuven.be", "root", new ArrayList<>(), "Tony"));


            rolservice.addRolToGebruiker("Admin", "Admin");
            rolservice.addRolToGebruiker("Admin", "Student");
            rolservice.addRolToGebruiker("robin.tourlamain@student.kuleuven.be", "Student");
            rolservice.addRolToGebruiker("fien.deleersnyder@student.kuleuven.be", "Student");
            rolservice.addRolToGebruiker("emmy.valckenier@student.kuleuven.be", "Student");
            rolservice.addRolToGebruiker("Coordinator", "Coordinator");
            rolservice.addRolToGebruiker("email", "Student");
            rolservice.addRolToGebruiker("tony.wauters@kuleuven.be", "Promotor");
            rolservice.addRolToGebruiker("telenetemail", "Bedrijf");
            rolservice.addRolToGebruiker("amail", "Promotor");

            for(int i = 0; i<20; i++){
                String fn = f.name().firstName();
                String un = f.internet().emailAddress(fn);
                String sn = "r" + f.number().digits(7);
                rolservice.saveGebruiker(new Student( f.name().lastName(),un,"root", new ArrayList<>(),fn,sn,0,0,f.address().fullAddress()));
                rolservice.addRolToGebruiker(un, "Student");
            }

        };
    }
    @Order(2)
    @Bean
    CommandLineRunner initDatabase(Onderwerprepository repository, Gebruikerservice gebruikerservice) {
        //voeg onderwerpen toe naar wens
        return args -> {
            log.info("Preloading " + repository.save(new Onderwerp("AI","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0475981642",1,"Beschrijving voor het onderwerp AI",new ArrayList<String>(List.of("Software Development")),new ArrayList<String>(List.of("Artificial intelligence")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("IT","IW E-ICT Gent",gebruikerservice.findByUsername("amail"),"annemie.vorstermans@kuleuven.be","0479648355",1,"Beschrijving voor het onderwerp IT",new ArrayList<String>(List.of("Data Science and Engineering")),new ArrayList<String>(List.of("Computer")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Upscaling","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0479648355",1,"Beschrijving voor het onderwerp upscaling",new ArrayList<String>(List.of("Systems Theory and Signal Processing")),new ArrayList<String>(),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Elektronica onderzoek 1","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0475981642",1,"Beschrijving voor het onderwerp elektronica onderzoek 1",new ArrayList<String>(List.of("Computer Technique, Microcontrollers and Operating Systems", "Analog Electronics and Design")),new ArrayList<String>(List.of("OS", "New techniques", "Designing")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Elektronica onderzoek 2","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0475981642",2,"Beschrijving voor het onderwerp elektronica onderzoek 2",new ArrayList<String>(List.of("Data Science and Engineering","Analog Electronics and Design","Digital Electronics and Design")),new ArrayList<String>(List.of("Digital", "Aanalog")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Elektronica onderzoek 3","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0475981642",1,"Beschrijving voor het onderwerp elektronica onderzoek 3",new ArrayList<String>(List.of("Analog Electronics and Design")),new ArrayList<String>(),true)));
            log.info("Preloading " + repository.save(new Onderwerp("High programming","IW E-ICT Gent",gebruikerservice.findByUsername("amail"),"annemie.vorstermans@kuleuven.be","0479648355",1,"Beschrijving voor het onderwerp high programming",new ArrayList<String>(List.of("Software Development")),new ArrayList<String>(List.of("Programming languages")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Datastructuren onderzoeken","IW E-ICT Gent",gebruikerservice.findByUsername("amail"),"annemie.vorstermans@kuleuven.be","0479648355",1,"Beschrijving  voor het onderwerp datastructuren onderzoeken",new ArrayList<String>(List.of("Data Science and Engineering")),new ArrayList<String>(),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Digitaal ontwerp","IW E-ICT Gent",gebruikerservice.findByUsername("tony.wauters@kuleuven.be"),"tony.wauters@kuleuven.be","0475981642",2,"Beschrijving voor het onderwerp digitaal ontwerp",new ArrayList<String>(List.of("Digital Electronics and Design")),new ArrayList<String>(List.of("Designing")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Databanken","IW E-ICT Gent",gebruikerservice.findByUsername("amail"),"annemie.vorstermans@kuleuven.be","0479648355",2,"Beschrijving voor het onderwerp databanken",new ArrayList<String>(List.of("Software Development")),new ArrayList<String>(List.of("SQL")),true)));
            log.info("Preloading " + repository.save(new Onderwerp("Systeem- en netwerkbeheer","IW E-ICT Gent",gebruikerservice.findByUsername("amail"),"annemie.vorstermans@kuleuven.be","0479648355",1,"Beschrijving voor het onderwerp systeem-en netwerkbeheer",new ArrayList<String>(List.of("Tele and Data communication")),new ArrayList<String>(),true)));
        };
    }

}
