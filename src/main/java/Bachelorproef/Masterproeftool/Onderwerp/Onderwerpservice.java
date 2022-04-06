package Bachelorproef.Masterproeftool.Onderwerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Onderwerpservice {

    private final Onderwerprepository onderwerprepository;
    @Autowired
    public Onderwerpservice(Onderwerprepository onderwerprepository){
        this.onderwerprepository = onderwerprepository;
    }

    public Onderwerp voegOnderwerpToe(Onderwerp tempOnderwerp) {
        return onderwerprepository.save(tempOnderwerp);
    }

    public Onderwerp getOnderwerpById(int id) {
        return onderwerprepository.findById(id).orElseThrow(() -> new Onderwerpnotfoundexception(id));
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
}
