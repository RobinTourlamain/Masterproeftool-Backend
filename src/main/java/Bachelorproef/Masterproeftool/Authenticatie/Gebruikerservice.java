package Bachelorproef.Masterproeftool.Authenticatie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Gebruikerservice {
    private final Gebruikerrepository gebruikerrepository;
    @Autowired
    public Gebruikerservice(Gebruikerrepository g){
        this.gebruikerrepository = g;
    }

    public Gebruiker findByUsername(String username){
        return gebruikerrepository.findByUsername(username);
    }


    public Gebruiker saveGebruiker(Gebruiker g) {
        return gebruikerrepository.save(g);
    }

    public List<Gebruiker> findAllGebruikers() {
        return gebruikerrepository.findAll();
    }
}
