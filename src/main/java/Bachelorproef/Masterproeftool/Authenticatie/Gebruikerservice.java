package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class Gebruikerservice {
    private final Gebruikerrepository gebruikerrepository;
    private final PasswordEncoder passwordEncoder;
    private final Rolrepository rolrepository;
    @Autowired
    public Gebruikerservice(PasswordEncoder p, Gebruikerrepository g, Rolrepository rolrepository){
        this.gebruikerrepository = g;
        this.passwordEncoder = p;
        this.rolrepository = rolrepository;
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

    public void selectOnderwerpen(Gebruiker g, List l) {
        g.getSelection().clear();
        g.getSelection().addAll(l);
        gebruikerrepository.save(g);
    }

    public Collection<Onderwerp> getSelection(Gebruiker g){
        return g.getSelection();
    }

    public List<Gebruiker> findAllPromotoren() {
        return gebruikerrepository.findByRollenContaining(rolrepository.findByName("Promotor"));
    }

    public Optional<Gebruiker> findById(int promotorid) {
        return gebruikerrepository.findById((long) promotorid);
    }
}
