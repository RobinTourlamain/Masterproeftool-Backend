package Bachelorproef.Masterproeftool.Phase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DummyPhase {
    private static final Logger log = LoggerFactory.getLogger(DummyPhase.class);

    @Bean
    CommandLineRunner initPhase(Phaserepository phaserepository) {
        return args -> {
            log.info("Preloading " + phaserepository.save(new Phases()));
        };
    }

}
