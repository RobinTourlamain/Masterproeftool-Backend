package Bachelorproef.Masterproeftool.Authenticatie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class Rolservice {
    private final Rolrepository rolrepository;  //in orde door @RequiredArgsConstructor tag, kan ook met @Autowire
    private final Gebruikerservice gebruikerservice;

    public Gebruiker saveGebruiker(Gebruiker g){
        log.info("saving new user to database");
        return gebruikerservice.saveGebruiker(g);
    }

    public Rol saveRol(Rol r){
        log.info("saving new role to database");
        return rolrepository.save(r);
    }

    public void addRolToGebruiker(String username, String rolnaam ){
        log.info("adding new role to user");
        Gebruiker gebruiker = gebruikerservice.findByUsername(username);    //Hier nog meer logica en checks nodig!!
        Rol rol = rolrepository.findByName(rolnaam);
        gebruiker.getRollen().add(rol);
    }

    public Gebruiker getGebruiker(String username){
        return gebruikerservice.findByUsername(username);
    }

    public List<Gebruiker> getGebruikers(){
        return gebruikerservice.findAllGebruikers();
    }
}
