package Bachelorproef.Masterproeftool.Onderwerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class Onderwerpservice {

    private final Onderwerprepository onderwerprepository;
    @Autowired
    public Onderwerpservice(Onderwerprepository onderwerprepository){
        this.onderwerprepository = onderwerprepository;
    }

    public List<Onderwerp> getAlleOnderwerpen(){
        return onderwerprepository.findAll();
    }

    public Onderwerp voegOnderwerpToe(Onderwerp tempOnderwerp) {
        return onderwerprepository.save(tempOnderwerp);
    }
}
