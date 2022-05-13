package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerpservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Student findStudentByUsername(String username){return gebruikerrepository.findStudentByUsername(username);}

    public Gebruiker saveGebruiker(Gebruiker g) {
        g.setPassword(passwordEncoder.encode(g.getPassword()));
        return gebruikerrepository.save(g);
    }

    public List<Gebruiker> findAllGebruikers() {
        return gebruikerrepository.findAll();
    }

    public void favoriteOnderwerp(Student g){
        gebruikerrepository.save(g);
    }

    public void deleteFavoriteOnderwerp(Student g, Onderwerp o) {
        g.getFavorites().remove(o);
        gebruikerrepository.save(g);
    }

    public void selectOnderwerpen(Student g, Map l) {
        g.setSelection(l);
        gebruikerrepository.save(g);
    }

    public Map<Integer,Integer> getSelection(Student g){
        return g.getSelection();
    }

    public List<Gebruiker> findAllPromotoren() {
        return gebruikerrepository.findByRollenContaining(rolrepository.findByName("Promotor"));
    }

    public Optional<Gebruiker> findById(int promotorid) {
        return gebruikerrepository.findById((long) promotorid);
    }

    public Student findStudentById(long sid) {
        return gebruikerrepository.findStudentById(sid);
    }

    public Student wijsToe(Onderwerp o, long sid) {
        Student s = gebruikerrepository.findStudentById(sid);
        s.setToegewezen(o);
        return gebruikerrepository.save(s);
    }
}
