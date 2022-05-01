package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Gebruikerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Onderwerpservice {

    private final Onderwerprepository onderwerprepository;
    private final Gebruikerservice gebruikerservice;
    @Autowired
    public Onderwerpservice(Onderwerprepository onderwerprepository, Gebruikerservice gebruikerservice){
        this.onderwerprepository = onderwerprepository;
        this.gebruikerservice = gebruikerservice;
    }

    public Onderwerp voegOnderwerpToe(Onderwerp tempOnderwerp, int promotorid) {
        if(gebruikerservice.findById(promotorid).isEmpty()){
            throw new Promotornotfoundexception(promotorid);
        }
        else{
            tempOnderwerp.setPromotor(gebruikerservice.findById(promotorid).orElse(gebruikerservice.findByUsername("Admin")));
            return onderwerprepository.save(tempOnderwerp);
        }
    }

    public Onderwerp voegOnderwerpToeKantEnKlaar(Onderwerp temp){
        return onderwerprepository.save(temp);
    }

    public Onderwerp getOnderwerpById(int id) {
        return onderwerprepository.findById(id).orElseThrow(() -> new Onderwerpnotfoundexception(id));
    }

    public List getOnderwerpenById(ArrayList ids){
        return onderwerprepository.findAllById(ids);
    }

    public Onderwerp vervangOnderwerp(Onderwerp temponderwerp, int id) {
        return onderwerprepository.findById(id)
                .map(onderwerp -> {
                    onderwerp.setName(temponderwerp.getName());
                    return onderwerprepository.save(onderwerp);
                })
                .orElseGet(() -> {
                    temponderwerp.setId(id);
                    return onderwerprepository.save(temponderwerp);
                });
    }

    public void deleteOnderwerp(int id) {
        onderwerprepository.deleteById(id);
    }

    public List<Onderwerp> getAlleOnderwerpen(String id) {
        return onderwerprepository.findAll(Sort.by(Sort.Direction.ASC, id));
    }

    public Onderwerp updateHideObject(Onderwerp onderwerp) {
        return onderwerprepository.save(onderwerp);
    }
}
