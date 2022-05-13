package Bachelorproef.Masterproeftool.Phase;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DummyPhase {
    private static final Logger log = LoggerFactory.getLogger(DummyPhase.class);

    @Bean
    CommandLineRunner initPhase(Phaserepository phaserepository) {
        return args -> {
            LocalDateTime n = LocalDateTime.now();
            log.info("Preloading " + phaserepository.save(new Phases(n,n.plusHours(1),n.plusHours(2),n.plusHours(3),n.plusHours(4),n.plusHours(5))));
        };
    }

}
