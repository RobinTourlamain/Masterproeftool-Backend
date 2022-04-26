package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Gebruikerservice {
    private final Gebruikerrepository gebruikerrepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public Gebruikerservice(PasswordEncoder p,Gebruikerrepository g){
        this.gebruikerrepository = g;
        this.passwordEncoder = p;
    }

    public Gebruiker findByUsername(String username){
        return gebruikerrepository.findByUsername(username);
    }

    public Gebruiker saveGebruiker(Gebruiker g) {
        g.setPassword(passwordEncoder.encode(g.getPassword()));
        return gebruikerrepository.save(g);
    }

    public List<Gebruiker> findAllGebruikers() {
        return gebruikerrepository.findAll();
    }

    public void favoriteOnderwerp(Gebruiker g, Onderwerp o){
        g.getFavorites().add(o);
        gebruikerrepository.save(g);
    }

    public void deleteFavoriteOnderwerp(Gebruiker g, Onderwerp o) {
        g.getFavorites().remove(o);
        gebruikerrepository.save(g);
    }
}
