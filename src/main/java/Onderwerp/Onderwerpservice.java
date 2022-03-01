package Onderwerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Onderwerpservice {

    private final Onderwerprepository onderwerprepository;
    @Autowired
    public Onderwerpservice(Onderwerprepository onderwerprepository){
        this.onderwerprepository = onderwerprepository;
    }

}
